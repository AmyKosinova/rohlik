package com.rohlik.rohlik.controller;

import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.mapping.ProductMapper;
import com.rohlik.rohlik.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mapper;

    @PostMapping
    public ResponseEntity createProduct(@Validated @RequestBody ProductDTO productDTO) {
        Assert.notNull(productDTO.getUnitPrice(), "product must have unit price specified");
        Assert.isTrue(productDTO.getUnitPrice().compareTo(BigDecimal.ZERO) > 0, "product must have positive unit price");
        Product product = productService.createProduct(productDTO);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        List<ProductDTO> dto = mapper.toDto(allProducts);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Validated @RequestBody ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping
    public ResponseEntity deleteProduct(@RequestBody ProductDTO productDTO) {
        productService.deleteProduct(productDTO);
        return ResponseEntity.ok().build();
    }
}
