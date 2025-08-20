package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Integer> {
    Optional<RestaurantOwner> findByTaxNumber(String taxNumber);

    Optional<RestaurantOwner> findByUserEmail(String email);

    Optional<RestaurantOwner> findByUserUsername(String userName);

    Optional<RestaurantOwner> findByUserId(Integer userId);
}
