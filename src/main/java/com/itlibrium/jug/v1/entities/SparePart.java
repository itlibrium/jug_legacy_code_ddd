package com.itlibrium.jug.v1.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class SparePart {

    @Id
    @GeneratedValue
    private int id;

    private double price;

}

