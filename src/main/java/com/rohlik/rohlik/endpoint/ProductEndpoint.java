package com.rohlik.rohlik.endpoint;

import com.rohlik.rohlik.endpoint.payload.OrderRequest;
import com.rohlik.rohlik.endpoint.payload.ProductDTO;
import com.rohlik.rohlik.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.RespectBinding;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("product/")
public class ProductEndpoint {

    private ProductService productService;

    @PostMapping
    public void createProduct(@Validated @RequestBody ProductDTO productDTO) {
        Assert.notNull(productDTO.getUnitPrice(),"product must have unit price specified");
        Assert.isTrue(productDTO.getUnitPrice().compareTo(BigDecimal.ZERO) > 0, "product must have positive unit price");
        productService.createProduct(productDTO);
    }

    @DeleteMapping
    public ResponseEntity deleteProduct(@RequestBody ProductDTO productDTO) {
        productService.deleteProduct(productDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Validated @RequestBody ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return ResponseEntity.ok(productDTO);
    }


}
