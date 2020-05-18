package com.rohlik.rohlik.endpoint.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = {"id"})
public class ProductDTO {

    private Long id;

    @NotNull
    @Positive
    private Long amount;

    @NotBlank
    private String name;

    private BigDecimal unitPrice;

}
