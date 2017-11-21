package com.itlibrium.jug.v1.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Equipment {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private EquipmentType equipmentType;
}
