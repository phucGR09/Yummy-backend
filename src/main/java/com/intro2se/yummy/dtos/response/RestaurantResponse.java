package com.intro2se.yummy.dtos.response;

import com.intro2se.yummy.entities.MenuItem;
import com.intro2se.yummy.entities.Restaurant;
import com.intro2se.yummy.entities.RestaurantOwner;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantResponse {
    Integer id;
    String name;
    String address;
    LocalTime openingHours;
    RestaurantOwner owner;

    public Restaurant toRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setOpeningHours(openingHours);
        restaurant.setOwner(owner);
        return restaurant;
    }

    public static RestaurantResponse fromRestaurant(Restaurant restaurant) {
        RestaurantResponse response = new RestaurantResponse();
        response.setId(restaurant.getId());
        response.setName(restaurant.getName());
        response.setAddress(restaurant.getAddress());
        response.setOpeningHours(restaurant.getOpeningHours());
        response.setOwner(restaurant.getOwner());
        return response;
    }
}
