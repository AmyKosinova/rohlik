package com.rohlik.rohlik.endpoint.payload;

import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class OrderRequest {
    private Long id;
    private Set<ProductDTO> products;

    public Set<ProductDTO> getProducts() {
        return products;
    }
}
