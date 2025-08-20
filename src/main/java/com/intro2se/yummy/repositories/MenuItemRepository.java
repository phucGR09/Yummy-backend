package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByRestaurantId(int restaurantId);

    List<MenuItem> findByPriceLessThan(BigDecimal price);

    List<MenuItem> findByNameContaining(String name);

    List<MenuItem> findByNameContainingIgnoreCase(String name);
}
