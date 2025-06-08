package com.rohlik.rohlik.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "PRODUCT")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Product extends CodedEntity<Long> {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "STOCK", nullable = false)
    private Long stock;

    @Column(name = "UNIT_PRICE", nullable = false)
    private BigDecimal unitPrice;

    public Product updateStock(Long delta) {
        stock += delta;
        return this;
    }

}
