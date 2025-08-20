package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.MenuItem;
import com.intro2se.yummy.entities.Restaurant;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItemUpdationRequest {
    @NotBlank(message = "INVALID_ID")
    Integer id;

    @NotBlank(message = "INVALID_NAME")
    String name;

    @NotBlank(message = "INVALID_PRICE")
    BigDecimal price;

    @NotBlank(message = "INVALID_DESCRIPTION")
    String description;

    @NotBlank(message = "INVALID_IMAGE_URL")
    String imageUrl;

    @NotBlank(message = "INVALID_RESTAURANT_ID")
    Integer restaurantId;

    @JsonIgnore
    Restaurant restaurant;

    public MenuItem toMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setDescription(description);
        menuItem.setImageUrl(imageUrl);
        menuItem.setRestaurant(restaurant);
        return menuItem;
    }
}
