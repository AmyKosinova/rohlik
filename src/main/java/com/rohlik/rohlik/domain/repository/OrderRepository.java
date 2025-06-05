package com.rohlik.rohlik.domain.repository;

import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.domain.OrderedProduct;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByCreationDateBeforeAndPaymentReceivedIsFalse(LocalDateTime creationDate);

    List<Order> findByProducts(OrderedProduct orderedProduct);

    List<Order> findByProductsIn(Collection<OrderedProduct> orderedProduct);

}
