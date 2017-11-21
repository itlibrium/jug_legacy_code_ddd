package com.itlibrium.jug.v1.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity
@Data
public class Certificate {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private CertificateCategory certificateCategory;

    private Instant expiresOn ;

}
