package com.rohlik.rohlik.endpoint.payload;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class OrderRequest {

    private Long id;
    private Set<ProductDTO> products;
    private BigDecimal incomingPayment;

}
