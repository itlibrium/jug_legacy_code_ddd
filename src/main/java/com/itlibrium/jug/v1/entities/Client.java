package com.itlibrium.jug.v1.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany
    private List<Equipment> equipments;


}
