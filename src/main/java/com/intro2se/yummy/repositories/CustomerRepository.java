package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUserEmail(String email);

    Optional<Customer> findByUserUsername(String username);

    Optional<Customer> findByUserPhoneNumber(String phone);
}
