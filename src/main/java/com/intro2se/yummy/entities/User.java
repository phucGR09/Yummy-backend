package com.intro2se.yummy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name", nullable = false, length = 50, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "phone_number", length = 50, unique = true)
    private String phoneNumber;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
