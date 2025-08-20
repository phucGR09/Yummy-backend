package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.DeliveryDriver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriver, Integer> {
    Optional<DeliveryDriver> findByLicensePlate(String licensePlate);

    Optional<DeliveryDriver> findByIdentityNumber(String email);

    Optional<DeliveryDriver> findByUserUsername(String userName);
}
