package com.rohlik.rohlik.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "ORDERED_PRODUCT")
@EqualsAndHashCode(callSuper = true)
public class OrderedProduct extends CodedEntity<Long> {

    @OneToOne
    @JoinColumn(name = "ORIGINAL_PRODUCT")
    private Product originalProduct;

    @Column(name = "AMOUNT")
    private Long amount;

}
