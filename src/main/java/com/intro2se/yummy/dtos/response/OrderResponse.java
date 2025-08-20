package com.intro2se.yummy.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.*;
import com.intro2se.yummy.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    private Integer id;
    private Restaurant restaurant;
    private DeliveryDriver driver;
    private Customer customer;
    private LocalDateTime orderTime;
    private BigDecimal totalPrice;
    private OrderStatus status;

    public Order toOrder() {
        Order order = new Order();
        order.setId(id);
        order.setRestaurant(restaurant);
        order.setDriver(driver);
        order.setCustomer(customer);
        order.setOrderTime(orderTime);
        order.setTotalPrice(totalPrice);
        order.setStatus(status);
        return order;
    }

    public static OrderResponse fromOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .restaurant(order.getRestaurant())
                .driver(order.getDriver())
                .customer(order.getCustomer())
                .orderTime(order.getOrderTime())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}
