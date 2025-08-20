package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.MenuItem;
import com.intro2se.yummy.entities.Order;
import com.intro2se.yummy.entities.OrderMenuItem;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderMenuItemUpdationRequest {
    @NotBlank(message = "INVALID_ID")
    Integer id;

    @JsonIgnore
    Order order;

    @JsonIgnore
    MenuItem menuItem;

    @NotBlank(message = "INVALID_ORDER_ID")
    Integer orderId;

    @NotBlank(message = "INVALID_MENU_ITEM_ID")
    Integer menuItemId;

    @NotBlank(message = "INVALID_QUANTITY")
    Integer quantity;

    String specialInstructions = "";

    public OrderMenuItem toOrderMenuItem() {
        OrderMenuItem orderMenuItem = new OrderMenuItem();
        orderMenuItem.setId(id);

        if (order == null) {
            throw new AppException(ErrorCode.NULL_ORDER);
        }
        orderMenuItem.setOrder(order);

        if (menuItem == null) {
            throw new AppException(ErrorCode.NULL_MENU_ITEM);
        }
        orderMenuItem.setMenuItem(menuItem);
        orderMenuItem.setQuantity(quantity);
        orderMenuItem.setSpecialInstructions(specialInstructions);
        return orderMenuItem;
    }
}
