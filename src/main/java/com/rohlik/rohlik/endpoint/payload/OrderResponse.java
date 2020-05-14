package com.rohlik.rohlik.endpoint.payload;

import com.rohlik.rohlik.domain.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class OrderResponse {

    private Long orderId;
    private Set<ProductDTO> missingProducts;
    private BigDecimal totalPrice;

}
