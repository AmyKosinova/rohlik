package com.rohlik.rohlik.service;

import com.google.common.collect.ImmutableSet;
import com.rohlik.rohlik.domain.CodedEntity;
import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.domain.repository.OrderRepository;
import com.rohlik.rohlik.domain.repository.ProductRepository;
import com.rohlik.rohlik.endpoint.payload.OrderRequest;
import com.rohlik.rohlik.endpoint.payload.ProductDTO;
import com.rohlik.rohlik.mapping.DefaultMapping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@Component
@AllArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    private DefaultMapping mapper;


    @Transactional
    public BigDecimal createOrder(OrderRequest orderRequest) {
        flushExpiredOrders();
        orderRequest.getProducts()
                .forEach(p -> {
                    productRepository.save(
                            productRepository.findById(p.getId())
                                    .orElseThrow(() -> new IllegalStateException("Product not found " + p))
                                    .updateStock(-p.getAmount()));
                });
        Order o = new Order();
        o.setProducts(mapper.toOrderedProduct(orderRequest.getProducts()));
        o.setCreationDate(LocalDateTime.now());
        orderRepository.save(o);
        return BigDecimal.valueOf(13.61);
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
        List<Order> byCreationDateBefore = orderRepository.findByCreationDateBefore(LocalDateTime.now().minusSeconds(15));
        if (!CollectionUtils.isEmpty(byCreationDateBefore)){
            for (Order order : byCreationDateBefore) {
                order.getProducts().forEach(p -> p.getOriginalProduct().updateStock(p.getAmount()));
            }
            orderRepository.deleteAll(byCreationDateBefore);
        }
    }

}
