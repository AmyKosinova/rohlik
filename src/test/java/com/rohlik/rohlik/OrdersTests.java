package com.rohlik.rohlik;

import com.google.common.collect.ImmutableSet;
import com.rohlik.rohlik.controller.payload.OrderRequest;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.domain.OrderedProduct;
import com.rohlik.rohlik.domain.repository.OrderRepository;
import com.rohlik.rohlik.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Random;
import java.util.Set;

class OrdersTests implements ContextTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @Test
    void shouldCalculateMissingProductAmount() {
        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .amount(100L)
                .build();

        Set<ProductDTO> unavailableProducts = orderService.getUnavailableProducts(ImmutableSet.of(productDTO));

        productDTO.setAmount(100 - 7L);
        Assertions.assertThat(unavailableProducts)
                .containsExactly(productDTO);
    }

    @Test
    @Transactional
    @Rollback(false)
    void shouldFindActiveOrdersByProduct() {
        System.err.println(orderRepository.findAll());

        createDefaultOrders();

        System.err.println(orderRepository.findAll());


        OrderedProduct op = new OrderedProduct();
        op.setId(1L);
        List<Order> OrdersByProduct = orderRepository.findByProducts(op);

        Assertions.assertThat(OrdersByProduct)
                .hasSize(1);
    }

    @Test
    @Transactional
    @Rollback(false)
    void shouldFindActiveOrdersByProducts() {
        System.err.println(orderRepository.findAll());


        createDefaultOrders();

        System.err.println(orderRepository.findAll());

        OrderedProduct op = new OrderedProduct();
        op.setId(1L);
        OrderedProduct op2 = new OrderedProduct();
        op2.setId(2L);

        List<Order> ordersByProductsIn = orderRepository.findByProductsIn(ImmutableSet.of(op, op2));
        Assertions.assertThat(ordersByProductsIn)
                .hasSize(2);
    }

    private void createDefaultOrders() {
        OrderRequest or = OrderRequest.builder()
                .id(new Random().nextLong())
                .products(ImmutableSet.of(ProductDTO.builder()
                        .id(1L)
                        .amount(1L)
                        .name("tst")
                        .build()))
                .build();
        orderService.createOrder(or);

        OrderRequest or2 = OrderRequest.builder()
                .id(new Random().nextLong())
                .products(ImmutableSet.of(ProductDTO.builder()
                        .id(2L)
                        .amount(1L)
                        .name("tst")
                        .build()))
                .build();
        orderService.createOrder(or2);
    }


}
