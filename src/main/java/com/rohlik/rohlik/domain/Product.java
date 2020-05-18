package com.rohlik.rohlik.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "PRODUCT")
@EqualsAndHashCode(callSuper = true)
public class Product extends CodedEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "STOCK")
    private Long stock;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    public Product updateStock(Long delta) {
        stock += delta;
        return this;
    }

}
