package com.rohlik.rohlik.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
