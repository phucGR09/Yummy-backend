package com.intro2se.yummy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "RESTAURANT_OWNERS")
@Getter
@Setter
public class RestaurantOwner {
    @Id
    private Integer id;

    @Column(name = "tax_number", length = 50, unique = true)
    private String taxNumber;

    @MapsId
    @OneToOne
    @JoinColumn(name = "owner_id")
    private User user;
}
