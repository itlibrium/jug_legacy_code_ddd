package com.itlibrium.jug.v1.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Serviceman {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    private List<Certificate> certificates;

}
