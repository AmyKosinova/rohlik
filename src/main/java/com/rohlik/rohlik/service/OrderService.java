package com.rohlik.rohlik.service;

import com.google.common.collect.ImmutableSet;
import com.rohlik.rohlik.controller.payload.OrderRequest;
import com.rohlik.rohlik.controller.payload.OrderResponse;
import com.rohlik.rohlik.controller.payload.PaymentRequest;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.domain.CodedEntity;
import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.domain.repository.OrderRepository;
import com.rohlik.rohlik.domain.repository.ProductRepository;
import com.rohlik.rohlik.mapping.OrderMapper;
import com.rohlik.rohlik.mapping.ProductMapper;
import com.rohlik.rohlik.properties.RohlikProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
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

    public Set<ProductDTO> getUnavailableProducts(Set<ProductDTO> products) {
        flushExpiredOrders();
        Map<Long, Product> requestedProducts = fetchRequestedProductsFromDatabase(products);

        return calculateMissingAmount(products, requestedProducts);
    }

    private Map<Long, Product> fetchRequestedProductsFromDatabase(Set<ProductDTO> products) {
        List<Long> productIds = products
                .stream()
                .map(p -> {
                    Assert.state(p.getId() != null, "Product ID must not be null");
                    return p.getId();
                })
                .toList();
        Map<Long, Product> requestedProducts = ImmutableSet.copyOf(productRepository.findAllById(productIds))
                .stream().collect(Collectors.toMap(CodedEntity::getId, p -> p));

        verifyProductsById(productIds, requestedProducts);
        return requestedProducts;
    }

    private void verifyProductsById(List<Long> productIds, Map<Long, Product> requestedProducts) {
        if (productIds.size() != requestedProducts.size() && !requestedProducts.keySet().containsAll(productIds)) {
            throw new IllegalStateException("Requested products ID unknown: " +
                    productIds.stream()
                            .filter(p -> !requestedProducts.containsKey(p))
                            .toList());
        }
    }

    private Set<ProductDTO> calculateMissingAmount(Set<ProductDTO> products, Map<Long, Product> requestedProducts) {
        return products.stream()
                .filter(p -> requestedProducts.get(p.getId()).getStock() < p.getAmount())
                .map(p -> {
                    Product product = requestedProducts.get(p.getId());
                    ProductDTO productDTO = productMapper.toDto(product);
                    productDTO.setAmount((p.getAmount() - product.getStock()));
                    return productDTO;
                })
                .collect(Collectors.toSet());
    }

    @Scheduled(fixedDelay = 10000)
    public void flushExpiredOrders() {
        log.info("Flushing expired orders");
        List<Order> ordersCreatedEarlier = orderRepository.findByCreationDateBeforeAndPaymentReceivedIsFalse(
                LocalDateTime.now().minusSeconds(rohlikProperties.getOrder().getExpirySeconds()));
        releaseReservedItems(ordersCreatedEarlier);
        log.info("Orders flushed: {}", ordersCreatedEarlier);
    }

    public void deleteOrder(Long orderId) {
        Order order = getOrder(orderId);
        releaseReservedItems(Set.of(order));
        orderRepository.delete(order);
    }

    private void releaseReservedItems(Collection<Order> ordersCreatedEarlier) {
        if (!CollectionUtils.isEmpty(ordersCreatedEarlier)) {
            Set<Product> releasedProducts = ordersCreatedEarlier.stream()
                    .flatMap(o -> o.getProducts().stream())
                    .map(p -> {
                        p.getOriginalProduct().updateStock(p.getAmount());
                        return p.getOriginalProduct();
                    })
                    .collect(Collectors.toSet());
            productRepository.saveAll(releasedProducts);
            orderRepository.deleteAll(ordersCreatedEarlier);
        }
    }


    @Transactional
    public Order registerPayment(PaymentRequest paymentRequest) {
        Order order = getOrder(paymentRequest.getOrderId());
        Assert.state(!order.isPaymentReceived(), "Order was already payed!");
        Assert.state(order.getTotalPrice().compareTo(paymentRequest.getPaymentAmount()) == 0,
                () -> "Order must be paid at once in exact amount. Price: " + order.getTotalPrice());

        order.setPaymentReceived(true);
        return orderRepository.save(order);

    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order with ID " + orderId + " does not exist!"));
    }
}
