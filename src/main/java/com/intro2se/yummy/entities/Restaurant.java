package com.intro2se.yummy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(
        name = "RESTAURANTS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"owner_id, name"})
        }
)
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "opening_hours")
    private LocalTime openingHours;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private RestaurantOwner owner;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;
}
