package com.rohlik.rohlik.endpoint.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Data
public class OrderDTO {
    private Long id;
    Set<ProductDTO> products;
}
