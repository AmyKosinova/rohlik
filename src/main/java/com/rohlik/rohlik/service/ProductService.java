package com.rohlik.rohlik.service;

import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.domain.repository.ProductRepository;
import com.rohlik.rohlik.endpoint.payload.ProductDTO;
import com.rohlik.rohlik.mapping.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @Transactional
    public void createProduct(ProductDTO productDTO) {
        if (!productRepository.findByName(productDTO.getName()).isEmpty()) {
            throw new IllegalStateException("Product with name: " + productDTO.getName() + " already exists");
        }
        Product product = productMapper.toProduct(productDTO);
        product.setId(null);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(ProductDTO productDTO) {
        Product product = getProduct(productDTO);
        productRepository.delete(product);
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO) {
        Product product = getProduct(productDTO);
        Product updatedProduct = productMapper.toProduct(productDTO);
        productRepository.save(updatedProduct);
    }

    private Product getProduct(ProductDTO productDTO) {
        Assert.notNull(productDTO.getId(), "Id of product must be provided for this operation");
        return productRepository.findById(productDTO.getId()).orElseThrow(() -> new IllegalStateException("Product with ID: " + productDTO.getId() + " does not exists!"));
    }

}

