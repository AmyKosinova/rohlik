package com.rohlik.rohlik.endpoint.payload;

import lombok.Data;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class OrderRequest {

    private Long id;

    @Valid
    @NotEmpty
    private Set<ProductDTO> products;

    private BigDecimal incomingPayment;

}
