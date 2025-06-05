package com.rohlik.rohlik.controller.payload;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class OrderResponse {

    private Long orderId;
    private Set<ProductDTO> missingProducts;
    private BigDecimal totalPrice;

}
