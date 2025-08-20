package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.Restaurant;
import com.intro2se.yummy.entities.RestaurantOwner;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantCreationRequest {
    @NotBlank(message = "INVALID_NAME")
    String name;

    @NotBlank(message = "INVALID_ADDRESS")
    String address;

    @NotBlank(message = "INVALID_OPENING_HOURS")
    LocalTime openingHours;

    @NotBlank(message = "INVALID_USER_NAME")
    String username;

    @JsonIgnore
    RestaurantOwner owner;

    public static Restaurant toRestaurant(RestaurantCreationRequest request) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setOpeningHours(request.getOpeningHours());

        if (request.getOwner() == null) {
            throw new AppException(ErrorCode.NULL_USER);
        }
        restaurant.setOwner(request.getOwner());
        restaurant.setMenuItems(List.of());

        return restaurant;
    }
}
