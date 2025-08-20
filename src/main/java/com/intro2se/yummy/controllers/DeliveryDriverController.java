package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.response.ApiResponse;
import com.intro2se.yummy.dtos.response.DeliveryDriverResponse;
import com.intro2se.yummy.services.DeliveryDriverService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/admin/")
public class DeliveryDriverController {
    DeliveryDriverService deliveryDriverService;

    @GetMapping("/delivery-drivers")
    public ApiResponse<List<DeliveryDriverResponse>> getAllDeliveryDrivers() {
        return ApiResponse.<List<DeliveryDriverResponse>>builder()
                .result(deliveryDriverService.getAllDeliveryDrivers())
                .build();
    }

    @GetMapping("/delivery-drivers/username")
    public ApiResponse<DeliveryDriverResponse> getDeliveryDriverByUsername(@RequestParam String username) {
        return ApiResponse.<DeliveryDriverResponse>builder()
                .result(deliveryDriverService.getDeliveryDriverByUsername(username))
                .build();
    }
}
