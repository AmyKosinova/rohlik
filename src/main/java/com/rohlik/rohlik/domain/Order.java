package com.rohlik.rohlik.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "AN_ORDER")
@EqualsAndHashCode(callSuper = true)
public class Order extends CodedEntity<Long> {

    @JoinTable(
            name = "PRODUCTS_OF_ORDER",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    @Cascade(CascadeType.ALL)
    @OneToMany(fetch = FetchType.EAGER)
    private Set<OrderedProduct> products;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    @Column(name = "PAYMENT_RECEIVED")
    private boolean paymentReceived;
}
