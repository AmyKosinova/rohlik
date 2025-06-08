package com.rohlik.rohlik.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Set<ProductDTO> missingProducts;
    private Set<ProductDTO> orderProducts;
    private BigDecimal totalPrice;

}
