package com.rohlik.rohlik.service;

import com.google.common.collect.ImmutableSet;
import com.rohlik.rohlik.domain.CodedEntity;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.domain.repository.ProductRepository;
import com.rohlik.rohlik.endpoint.request.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    public void createOrder() {

    }

    public Map<ProductDTO, Integer> areProductsAvailable(Set<ProductDTO> products) {
        Map<Long, Product> requestedProducts = ImmutableSet.copyOf(productRepository.findAllById(products
                .stream()
                .map(ProductDTO::getId)
                .collect(Collectors.toList())))
                .stream().collect(Collectors.toMap(CodedEntity::getId, p -> p));

        Assert.state(products.size() == requestedProducts.size(), () -> "Obtained id out of DB!");

        return products.stream()
                .filter(p -> requestedProducts.get(p.getId()).getStock() < p.getAmount())
                .collect(Collectors.toMap(p -> p, p -> (int) (p.getAmount() - requestedProducts.get(p.getId()).getStock())));
    }

}
