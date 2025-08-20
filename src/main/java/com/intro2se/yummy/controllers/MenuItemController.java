package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.request.MenuItemCreationRequest;
import com.intro2se.yummy.dtos.request.MenuItemUpdationRequest;
import com.intro2se.yummy.dtos.response.ApiResponse;
import com.intro2se.yummy.dtos.response.MenuItemResponse;
import com.intro2se.yummy.services.MenuItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/admin/")
public class MenuItemController {
    MenuItemService menuItemService;

    @GetMapping("/menu-items")
    public ApiResponse<List<MenuItemResponse>> getAllMenuItems() {
        return ApiResponse.<List<MenuItemResponse>>builder()
                .result(menuItemService.getAllMenuItems())
                .build();
    }

    @GetMapping("/menu-items/restaurant-id")
    public ApiResponse<List<MenuItemResponse>> getMenuItemsByRestaurantId(@RequestParam(name = "id") Integer restaurantId) {
        return ApiResponse.<List<MenuItemResponse>>builder()
                .result(menuItemService.getMenuItemsByRestaurantId(restaurantId))
                .build();
    }

    @GetMapping("/menu-items/contained-dish-name/name")
    public ApiResponse<List<MenuItemResponse>> getMenuItemsByContainedDishName(@RequestParam String name) {
        return ApiResponse.<List<MenuItemResponse>>builder()
                .result(menuItemService.getMenuItemsContainingDishName(name))
                .build();
    }

    @PostMapping("/menu-items/create")
    public ApiResponse<MenuItemResponse> createMenuItem(@RequestBody MenuItemCreationRequest request) {
        return ApiResponse.<MenuItemResponse>builder()
                .result(menuItemService.createMenuItems(request))
                .build();
    }

    @DeleteMapping("/menu-items/delete/id")
    public ApiResponse<Boolean> deleteMenuItem(@RequestParam Integer id) {
        menuItemService.deleteMenuItem(id);
        return ApiResponse.<Boolean>builder()
                .message("Menu item deleted successfully")
                .result(true)
                .build();
    }

    @PatchMapping("/menu-items/update")
    public ApiResponse<MenuItemResponse> updateMenuItem(@RequestBody MenuItemUpdationRequest request) {
        return ApiResponse.<MenuItemResponse>builder()
                .result(menuItemService.updateMenuItem(request))
                .build();
    }
}
