package com.rohlik.rohlik.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public class CodedEntity<PK extends Serializable> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private PK id;

}
