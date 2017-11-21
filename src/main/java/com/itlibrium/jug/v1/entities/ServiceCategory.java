package com.itlibrium.jug.v1.entities;

import com.itlibrium.jug.v1.entities.CertificateCategory;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ServiceCategory {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    private List<CertificateCategory> RequiredCertificates;

    private String name;

    private double basePrice;

    private double pricePerHour;

}
