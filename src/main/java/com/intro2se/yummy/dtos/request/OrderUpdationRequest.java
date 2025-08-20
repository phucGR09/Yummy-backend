package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.*;
import com.intro2se.yummy.enums.OrderStatus;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
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
public class OrderUpdationRequest {
    @NotBlank(message = "INVALID_ID")
    Integer id;

    @JsonIgnore
    Restaurant restaurant;

    @JsonIgnore
    DeliveryDriver driver;

    @JsonIgnore
    Customer customer;

    @NotBlank(message = "INVALID_RESTAURANT_ID")
    Integer restaurantId;

    String driverUsername;

    @NotBlank(message = "INVALID_CUSTOMER_NAME")
    String customerUsername;

    @NotBlank(message = "INVALID_ORDER_TIME")
    LocalDateTime orderTime;

    @NotBlank(message = "INVALID_DELIVERY_TIME")
    BigDecimal totalPrice;

    @NotBlank(message = "INVALID_STATUS")
    OrderStatus status;

    public Order toOrder() {
        Order order = new Order();
        order.setId(id);
        if (restaurant == null) {
            throw new AppException(ErrorCode.NULL_RESTAURANT);
        }
        order.setRestaurant(restaurant);

        if (customer == null) {
            throw new AppException(ErrorCode.NULL_CUSTOMER);
        }

        order.setDriver(driver);
        order.setCustomer(customer);
        order.setOrderTime(orderTime);
        order.setTotalPrice(totalPrice);
        order.setStatus(status);
        return order;
    }
}
