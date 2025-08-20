package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.Order;
import com.intro2se.yummy.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerId(Integer id);

    List<Order> findByRestaurantId(Integer id);

    List<Order> findByDriverId(Integer id);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerUserUsername(String username);

    List<Order> findByDriverUserUsername(String username);

    List<Order> findByCustomerUserUsernameAndStatus(String username, OrderStatus status);

    List<Order> findByRestaurantIdAndStatus(Integer restaurantId, OrderStatus status);

    @Query(value = "select order from Order order where order.orderTime >= ?1 and order.orderTime <= ?2")
    List<Order> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
}
