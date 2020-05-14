package com.rohlik.rohlik.endpoint.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
public class ProductDTO {

    private Long id;
    private Long amount;
    private String name;

}
