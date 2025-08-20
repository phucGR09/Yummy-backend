package com.intro2se.yummy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ADMINISTRATORS")
@Getter
@Setter
public class Administrator {
    @Id
    private Integer id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "admin_id")
    private User user;
}
