package com.intro2se.yummy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DELIVERY_DRIVERS")
@Getter
@Setter
public class DeliveryDriver {
    @Id
    private Integer id;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "license_plate", length = 50, unique = true)
    private String licensePlate;

    @Column(name = "identity_number", length = 50, unique = true)
    private String identityNumber;

    @MapsId
    @OneToOne
    @JoinColumn(name = "driver_id")
    private User user;
}
