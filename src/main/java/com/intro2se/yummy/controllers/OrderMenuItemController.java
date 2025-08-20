package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.request.OrderMenuItemCreationRequest;
import com.intro2se.yummy.dtos.request.OrderMenuItemUpdationRequest;
import com.intro2se.yummy.dtos.response.ApiResponse;
import com.intro2se.yummy.dtos.response.OrderMenuItemResponse;
import com.intro2se.yummy.services.OrderMenuItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/admin/")
public class OrderMenuItemController {
    OrderMenuItemService orderMenuItemService;

    @GetMapping("/order-menu-items")
    public ApiResponse<List<OrderMenuItemResponse>> getAllOrderMenuItems() {
        return ApiResponse.<List<OrderMenuItemResponse>>builder()
                .result(orderMenuItemService.getAllOrderMenuItems())
                .build();
    }

    @GetMapping("/order-menu-items/order-id")
    public ApiResponse<List<OrderMenuItemResponse>> getOrderMenuItemsByOrderId(@RequestParam(name = "id") Integer orderId) {
        return ApiResponse.<List<OrderMenuItemResponse>>builder()
                .result(orderMenuItemService.getOrderMenuItemsByOrderId(orderId))
                .build();
    }

    @GetMapping("/order-menu-items/menu-item-id")
    public ApiResponse<List<OrderMenuItemResponse>> getOrderMenuItemsByMenuItemId(@RequestParam Integer menuItemId) {
        return ApiResponse.<List<OrderMenuItemResponse>>builder()
                .result(orderMenuItemService.getOrderMenuItemsByMenuItemId(menuItemId))
                .build();
    }

    @PostMapping("/order-menu-items/create")
    public ApiResponse<OrderMenuItemResponse> createOrderMenuItems(@RequestBody OrderMenuItemCreationRequest request) {
        return ApiResponse.<OrderMenuItemResponse>builder()
                .result(orderMenuItemService.createOrderMenuItems(request))
                .build();
    }

    @DeleteMapping("/order-menu-items/delete/id")
    public ApiResponse<Boolean> deleteOrderMenuItem(@RequestParam Integer id) {
        orderMenuItemService.deleteOrderMenuItem(id);
        return ApiResponse.<Boolean>builder()
                .message("Order menu item deleted successfully")
                .result(true)
                .build();
    }

    @PatchMapping("/order-menu-items/update")
    public ApiResponse<OrderMenuItemResponse> updateOrderMenuItem(@RequestBody OrderMenuItemUpdationRequest request) {
        return ApiResponse.<OrderMenuItemResponse>builder()
                .result(orderMenuItemService.updateOrderMenuItem(request))
                .build();
    }
}
