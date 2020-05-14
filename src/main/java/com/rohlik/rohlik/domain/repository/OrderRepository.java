package com.rohlik.rohlik.domain.repository;

import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByCreationDateBefore(LocalDateTime creationDate);

}
