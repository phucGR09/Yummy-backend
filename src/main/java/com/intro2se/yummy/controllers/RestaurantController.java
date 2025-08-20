package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.request.RestaurantCreationRequest;
import com.intro2se.yummy.dtos.request.RestaurantUpdationRequest;
import com.intro2se.yummy.dtos.response.ApiResponse;
import com.intro2se.yummy.dtos.response.RestaurantResponse;
import com.intro2se.yummy.services.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/admin/")
public class RestaurantController {
    RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public ApiResponse<List<RestaurantResponse>> getAllRestaurants() {
        return ApiResponse.<List<RestaurantResponse>>builder()
                .result(restaurantService.getAllRestaurants())
                .build();
    }

    @GetMapping("/restaurants/username")
    public ApiResponse<List<RestaurantResponse>> getRestaurantByUsername(@RequestParam String username) {
        return ApiResponse.<List<RestaurantResponse>>builder()
                .result(restaurantService.getRestaurantByOwnerUsername(username))
                .build();
    }

    @GetMapping("/restaurants/id")
    public ApiResponse<RestaurantResponse> getRestaurantById(@RequestParam Integer id) {
        return ApiResponse.<RestaurantResponse>builder()
                .result(restaurantService.getRestaurantById(id))
                .build();
    }

    @PostMapping("/restaurants/create")
    public ApiResponse<RestaurantResponse> createRestaurant(@RequestBody RestaurantCreationRequest restaurantResponse) {
        return ApiResponse.<RestaurantResponse>builder()
                .result(restaurantService.createRestaurant(restaurantResponse))
                .build();
    }

    @PatchMapping("/restaurants/update")
    public ApiResponse<RestaurantResponse> updateRestaurant(@RequestBody RestaurantUpdationRequest restaurantResponse) {
        return ApiResponse.<RestaurantResponse>builder()
                .result(restaurantService.updateRestaurant(restaurantResponse))
                .build();
    }

    @DeleteMapping("/restaurants/delete/id")
    public ApiResponse<Boolean> deleteRestaurant(@RequestParam Integer id) {
        restaurantService.deleteRestaurantById(id);

        return ApiResponse.<Boolean>builder()
                .message("Restaurant deleted successfully")
                .result(true)
                .build();
    }
}
