package com.itlibrium.jug.v1.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Service {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    public Client client;

    @ManyToOne
    public ServiceCategory serviceCategory;

    @ManyToOne
    public Serviceman serviceman;

    public ServiceStatus status;

    public int duration;

    @OneToMany
    public List<SparePart> spareParts;

    public double price;

}
