package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Optional<Restaurant> findByName(String name);

    List<Restaurant> findByOwnerId(Integer id);

    List<Restaurant> findByAddressContaining(String address);

    List<Restaurant> findByOpeningHoursBefore(LocalTime time);

    List<Restaurant> findByOwnerUserUsername(String name);
}
