package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.MenuItem;
import com.intro2se.yummy.entities.Restaurant;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItemCreationRequest {
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

        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setDescription(description);
        menuItem.setImageUrl(imageUrl);

        if (restaurant == null) {
            throw new AppException(ErrorCode.RESTAURANT_NOT_FOUND);
        }
        menuItem.setRestaurant(restaurant);

        return menuItem;
    }
}
