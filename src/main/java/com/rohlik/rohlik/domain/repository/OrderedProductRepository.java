package com.rohlik.rohlik.domain.repository;

import com.rohlik.rohlik.domain.OrderedProduct;
import com.rohlik.rohlik.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderedProductRepository extends CrudRepository<OrderedProduct, Long> {


    List<OrderedProduct> findByOriginalProduct(Product product);
}
