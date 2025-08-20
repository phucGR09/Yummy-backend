package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.request.CustomerUpdateRequest;
import com.intro2se.yummy.dtos.response.ApiResponse;
import com.intro2se.yummy.dtos.response.CustomerResponse;
import com.intro2se.yummy.services.CustomerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/admin/")
public class CustomerController {
    CustomerService customerService;

    @GetMapping("/customers")
    public ApiResponse<List<CustomerResponse>> getAllCustomers() {
        return ApiResponse.<List<CustomerResponse>>builder()
                .result(customerService.getAllCustomers())
                .build();
    }

    @GetMapping("/customers/username")
    public ApiResponse<CustomerResponse> getCustomerByUsername(@RequestParam String username) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.getCustomerByUsername(username))
                .build();
    }

    @PutMapping("customers/update")
    public ApiResponse<CustomerResponse> updateCustomerByUsername(@RequestParam String username, @RequestBody @Valid CustomerUpdateRequest request) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.updateCustomerByUsername(username, request))
                .build();
    }
}
