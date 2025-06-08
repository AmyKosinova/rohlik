package com.rohlik.rohlik.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

@Data
@MappedSuperclass
public class CodedEntity<K extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private K id;

}
