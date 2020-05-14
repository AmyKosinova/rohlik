package com.rohlik.rohlik.endpoint;

import com.rohlik.rohlik.endpoint.payload.OrderRequest;
import com.rohlik.rohlik.endpoint.payload.ProductDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product/")
public class ProductEndpoint {

    @PostMapping
    public void createProduct(@RequestBody ProductDTO productDTO) {

    }

    @DeleteMapping
    public void deleteProduct(Long productId) {

    }

    @PutMapping
    public void updateOder(OrderRequest orderRequest) {

    }


}
