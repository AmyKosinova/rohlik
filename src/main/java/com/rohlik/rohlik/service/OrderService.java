package com.rohlik.rohlik.service;

import com.google.common.collect.ImmutableSet;
import com.rohlik.rohlik.properties.RohlikProperties;
import com.rohlik.rohlik.domain.CodedEntity;
import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.domain.repository.OrderRepository;
import com.rohlik.rohlik.domain.repository.ProductRepository;
import com.rohlik.rohlik.controller.payload.OrderRequest;
import com.rohlik.rohlik.controller.payload.OrderResponse;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.mapping.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RohlikProperties rohlikProperties;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        flushExpiredOrders();
        orderRequest.getProducts()
                .forEach(p -> productRepository.save(
                        productRepository.findById(p.getId())
                                .orElseThrow(() -> new IllegalStateException("Product not found " + p))
                                .updateStock(-p.getAmount())));
        Order order = createOrderInternal(orderRequest);

        return OrderResponse.builder()
                .totalPrice(order.getTotalPrice())
                .orderId(order.getId())
                .build();
    }

    private Order createOrderInternal(OrderRequest orderRequest) {
        Order order = new Order();
        order.setProducts(orderMapper.toOrderedProduct(orderRequest.getProducts()));
        order.getProducts().forEach(op -> op.setOriginalProduct(productRepository.findById(op.getOriginalProduct().getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID: " + op.getOriginalProduct().getId() + "does not exist!"))));
        order.setCreationDate(LocalDateTime.now());

        order.setTotalPrice(
                order.getProducts().stream()
                        .map(p -> p.getOriginalProduct().getUnitPrice().multiply(BigDecimal.valueOf(p.getAmount())))
                        .reduce(BigDecimal::add)
                        .orElseThrow(() -> new IllegalStateException("Orders without price are not allowed"))
        );
        return orderRepository.save(order);
    }

    public Set<ProductDTO> areProductsAvailable(Set<ProductDTO> products) {
        flushExpiredOrders();
        Map<Long, Product> requestedProducts = ImmutableSet.copyOf(productRepository.findAllById(products
                        .stream()
                        .map(ProductDTO::getId)
                        .collect(Collectors.toList())))
                .stream().collect(Collectors.toMap(CodedEntity::getId, p -> p));

        Assert.state(products.size() == requestedProducts.size(), () -> "Obtained id out of DB! " +
                products
                        .stream()
                        .filter(p -> !requestedProducts.containsKey(p.getId()))
                        .collect(Collectors.toList()));

        return products.stream()
                .filter(p -> requestedProducts.get(p.getId()).getStock() < p.getAmount())
                .peek(p -> p.setAmount((p.getAmount() - requestedProducts.get(p.getId()).getStock())))
                .collect(Collectors.toSet());
    }

    public void flushExpiredOrders() {
        List<Order> ordersCreatedEarlier = orderRepository.findByCreationDateBeforeAndPaymentReceivedIsFalse(
                LocalDateTime.now().minusSeconds(rohlikProperties.order.expirySeconds));
        if (!CollectionUtils.isEmpty(ordersCreatedEarlier)) {
            ordersCreatedEarlier.stream()
                    .flatMap(o -> o.getProducts().stream())
                    .forEach(p -> p.getOriginalProduct().updateStock(p.getAmount()));
            orderRepository.deleteAll(ordersCreatedEarlier);
        }
    }

    public void deleteOrder(Long orderId) {
        Order order = getOrder(orderId);
        orderRepository.delete(order);
    }

    @Transactional
    public void registerPayment(OrderRequest orderRequest) {
        Order order = getOrder(orderRequest.getId());
        Assert.state(!order.isPaymentReceived(), "Order was already payed!");
        Assert.state(order.getTotalPrice().compareTo(orderRequest.getIncomingPayment()) == 0, () -> "Payment does not match order value.");

        order.setPaymentReceived(true);
        orderRepository.save(order);
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order with ID " + orderId + " does not exist!"));
    }
}
