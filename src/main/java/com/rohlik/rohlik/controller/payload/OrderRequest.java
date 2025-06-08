package com.rohlik.rohlik.controller.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long id;

    @Valid
    @NotEmpty
    private Set<ProductDTO> products;
}
