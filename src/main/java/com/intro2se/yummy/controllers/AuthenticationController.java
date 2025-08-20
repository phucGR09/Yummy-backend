package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.request.*;
import com.intro2se.yummy.dtos.response.*;
import com.intro2se.yummy.services.AuthenticationService;
import com.intro2se.yummy.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/auth/")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(authenticationService.registerUser(request))
                .build();
    }

    @PostMapping("/complete/delivery-driver")
    public ApiResponse<DeliveryDriverResponse> createDeliveryDriver(@RequestBody @Valid DeliveryDriverCreationRequest request) {
        return ApiResponse.<DeliveryDriverResponse>builder()
                .result(authenticationService.completeDeliveryDriver(request))
                .build();
    }

    @PostMapping("/complete/customer")
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody @Valid CustomerCreationRequest request) {
        return ApiResponse.<CustomerResponse>builder()
                .result(authenticationService.completeCustomer(request))
                .build();
    }

    @PostMapping("/complete/restaurant-owner")
    public ApiResponse<RestaurantOwnerResponse> createRestaurantOwner(@RequestBody @Valid RestaurantOwnerCreationRequest request) {
        return ApiResponse.<RestaurantOwnerResponse>builder()
                .result(authenticationService.completeRestaurantOwner(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticateResponse> login(@RequestBody @Valid AuthenticateRequest request) {
        return ApiResponse.<AuthenticateResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }
}
