package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.OrderMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuItemRepository extends JpaRepository<OrderMenuItem, Integer> {
    List<OrderMenuItem> findByOrderId(int orderId);

    List<OrderMenuItem> findByQuantityGreaterThan(int quantity);

    List<OrderMenuItem> findByMenuItemId(int itemId);

    void deleteByOrderId(int orderId);
}
