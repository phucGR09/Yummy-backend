package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.request.UserCreationRequest;
import com.intro2se.yummy.dtos.request.UserUpdateRequest;
import com.intro2se.yummy.dtos.response.ApiResponse;
import com.intro2se.yummy.dtos.response.UserResponse;
import com.intro2se.yummy.services.UserService;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/admin/")
public class UserController {
    UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users/create")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }


    @GetMapping("/users/username")
    public ApiResponse<UserResponse> getUserByUsername(@RequestParam String username) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserByUsername(username))
                .build();
    }

    @PutMapping("users/username")
    public ApiResponse<UserResponse> updateUserByUsername(@RequestParam String username,@RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUserByUsername(username, request))
                .build();
    }
}
