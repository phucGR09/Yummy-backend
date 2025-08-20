package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.response.ApiResponse;
import com.intro2se.yummy.dtos.response.RestaurantOwnerResponse;
import com.intro2se.yummy.services.RestaurantOwnerService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/admin/")
public class RestaurantOwnerController {
    RestaurantOwnerService restaurantOwnerService;

    @GetMapping("/restaurant-owners")
    public ApiResponse<List<RestaurantOwnerResponse>> getAllRestaurantOwners() {
        return ApiResponse.<List<RestaurantOwnerResponse>>builder()
                .result(restaurantOwnerService.getAllRestaurantOwners())
                .build();
    }

    @GetMapping("/restaurant-owners/username")
    public ApiResponse<RestaurantOwnerResponse> getRestaurantOwnerByUsername(@RequestParam String username) {
        return ApiResponse.<RestaurantOwnerResponse>builder()
                .result(restaurantOwnerService.getRestaurantOwnerByUserName(username))
                .build();
    }
}
