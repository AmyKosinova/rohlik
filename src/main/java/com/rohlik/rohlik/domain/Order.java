package com.rohlik.rohlik.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "AN_ORDER")
@EqualsAndHashCode(callSuper = true)
public class Order extends CodedEntity<Long> {

    @OneToMany
    @JoinTable(
            name="ORDERED_PRODUCTS",
            joinColumns = @JoinColumn( name="ORDER_ID"),
            inverseJoinColumns = @JoinColumn( name="PRODUCT_ID")
    )
    List<Product> products;


}
