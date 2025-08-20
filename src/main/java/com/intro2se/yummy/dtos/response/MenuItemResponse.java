package com.intro2se.yummy.dtos.response;

import com.intro2se.yummy.entities.MenuItem;
import com.intro2se.yummy.entities.Restaurant;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItemResponse {
    Integer id;
    String name;
    BigDecimal price;
    String description;
    String imageUrl;
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

    public static MenuItemResponse fromMenuItem(MenuItem menuItem) {
        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .imageUrl(menuItem.getImageUrl())
                .restaurant(menuItem.getRestaurant())
                .build();
    }
}
