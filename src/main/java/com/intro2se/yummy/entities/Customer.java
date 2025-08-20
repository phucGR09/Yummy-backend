package com.intro2se.yummy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMERS")
@Getter
@Setter
public class Customer {
    @Id
    private Integer id;

    @Column(name = "address")
    private String address;

    @MapsId
    @OneToOne
    @JoinColumn(name = "customer_id")
    private User user;
}