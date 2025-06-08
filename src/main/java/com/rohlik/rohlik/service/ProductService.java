package com.rohlik.rohlik.service;

import com.google.common.collect.ImmutableList;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.domain.OrderedProduct;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.domain.repository.OrderRepository;
import com.rohlik.rohlik.domain.repository.OrderedProductRepository;
import com.rohlik.rohlik.domain.repository.ProductRepository;
import com.rohlik.rohlik.mapping.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final OrderRepository orderRepository;
    private final ProductMapper productMapper;

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        if (!productRepository.findByName(productDTO.getName()).isEmpty()) {
            throw new IllegalStateException("Product with name: " + productDTO.getName() + " already exists");
        }
        Product product = productMapper.toProduct(productDTO);
        product.setId(null);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(ProductDTO productDTO) {
        Product product = getProduct(productDTO);

        List<OrderedProduct> orderedProducts = orderedProductRepository.findByOriginalProduct(product);
        List<Order> productsInActiveOrders = orderRepository.findByProductsIn(orderedProducts);

        if (CollectionUtils.isEmpty(productsInActiveOrders)) {
            log.info("deleting product with id: {}", product.getId());
            productRepository.delete(product);
        } else {
            throw new IllegalStateException("Product with id: " + product.getId() + " exists in active orders");
        }
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO) {
        Product product = getProduct(productDTO);
        productMapper.update(productDTO, product);
        productRepository.save(product);
    }

    private Product getProduct(ProductDTO productDTO) {
        Assert.notNull(productDTO.getId(), "Id of product must be provided for this operation");
        return productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID: " + productDTO.getId() + " does not exists!"));
    }

    public List<Product> getAllProducts() {
        Iterable<Product> allProducts = productRepository.findAll();
        return ImmutableList.copyOf(allProducts);

    }
}

