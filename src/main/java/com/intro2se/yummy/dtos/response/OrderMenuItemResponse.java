package com.intro2se.yummy.dtos.response;

import com.intro2se.yummy.entities.MenuItem;
import com.intro2se.yummy.entities.Order;
import com.intro2se.yummy.entities.OrderMenuItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderMenuItemResponse {
    private Integer id;
    private Order order;
    private MenuItem menuItem;
    private Integer quantity;
    private String specialInstructions;

    public OrderMenuItem toOrder() {
        OrderMenuItem orderMenuItem = new OrderMenuItem();
        orderMenuItem.setId(id);
        orderMenuItem.setMenuItem(menuItem);
        orderMenuItem.setQuantity(quantity);
        orderMenuItem.setSpecialInstructions(specialInstructions);
        return orderMenuItem;
    }

    public static OrderMenuItemResponse fromOrderMenuItem(OrderMenuItem orderMenuItem) {
        return OrderMenuItemResponse.builder()
                .id(orderMenuItem.getId())
                .order(orderMenuItem.getOrder())
                .menuItem(orderMenuItem.getMenuItem())
                .quantity(orderMenuItem.getQuantity())
                .specialInstructions(orderMenuItem.getSpecialInstructions())
                .build();
    }
}
