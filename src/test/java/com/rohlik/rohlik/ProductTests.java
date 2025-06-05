package com.rohlik.rohlik;

import com.google.common.collect.ImmutableSet;
import com.rohlik.rohlik.controller.payload.OrderRequest;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.service.OrderService;
import com.rohlik.rohlik.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Random;

class ProductTests implements ContextTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @Test
    void shouldRefuseProductDeletion() {
        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .amount(1L)
                .name("tst")
                .build();
        orderService.createOrder(OrderRequest.builder()
                .id(new Random().nextLong())
                .products(ImmutableSet.of(productDTO))
                .build());

        Assertions.assertThatThrownBy(() -> productService.deleteProduct(productDTO))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldCreateAndDeleteProduct() {
        ProductDTO productDTO = ProductDTO.builder()
                .amount(1L)
                .name("tst")
                .unitPrice(BigDecimal.ONE)
                .build();

        Product product = productService.createProduct(productDTO);
        productDTO.setId(product.getId());

        Assertions.assertThat(productService.getAllProducts())
                .extracting("id")
                .isNotEmpty()
                .contains(productDTO.getId());

        productService.deleteProduct(productDTO);
        Assertions.assertThat(productService.getAllProducts())
                .extracting("id")
                .isNotEmpty()
                .doesNotContain(productDTO.getId());
    }

}
