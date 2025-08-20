package com.intro2se.yummy.controllers;

import com.intro2se.yummy.dtos.request.OrderCreationRequest;
import com.intro2se.yummy.dtos.request.OrderUpdationRequest;
import com.intro2se.yummy.dtos.response.ApiResponse;
import com.intro2se.yummy.dtos.response.OrderResponse;
import com.intro2se.yummy.enums.OrderStatus;
import com.intro2se.yummy.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/admin/")
public class OrderController {
    OrderService orderService;

    @GetMapping("/orders")
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrders())
                .build();
    }

    @GetMapping("/orders/customer-id")
    public ApiResponse<List<OrderResponse>> getOrderByCustomerId(@RequestParam(name = "customer-id") Integer customerId) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByCustomerId(customerId))
                .build();
    }

    @GetMapping("/orders/customer-username")
    public ApiResponse<List<OrderResponse>> getOrderByCustomerUsername(@RequestParam String username) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByCustomerUsername(username))
                .build();
    }

    @GetMapping("/orders/restaurant-id")
    public ApiResponse<List<OrderResponse>> getOrdersByRestaurantId(@RequestParam(name = "restaurant-id") Integer restaurantId) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByRestaurantId(restaurantId))
                .build();
    }

    @GetMapping("/orders/status")
    public ApiResponse<List<OrderResponse>> getOrdersByStatus(@RequestParam OrderStatus status) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByStatus(status))
                .build();
    }

    @GetMapping("/orders/customer-username-and-status")
    public ApiResponse<List<OrderResponse>> getOrdersByCustomerUsernameAndStatus(@RequestParam String username, @RequestParam OrderStatus status) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByCustomerUsernameAndStatus(username, status))
                .build();
    }

    @GetMapping("/orders/restaurant-id-and-status")
    public ApiResponse<List<OrderResponse>> getOrdersByRestaurantIdAndStatus(@RequestParam Integer restaurantId, @RequestParam OrderStatus status) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByRestaurantIdAndStatus(restaurantId, status))
                .build();
    }

    @GetMapping("/orders/driver-id")
    public ApiResponse<List<OrderResponse>> getOrdersByDriverId(@RequestParam Integer driverId) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByDriverId(driverId))
                .build();
    }

    @GetMapping("/orders/driver-username")
    public ApiResponse<List<OrderResponse>> getOrdersByDriverUsername(@RequestParam String username) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByDriverUsername(username))
                .build();
    }

    @PostMapping("/orders/create")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    @PatchMapping("/orders/update")
    public ApiResponse<OrderResponse> updateOrder(@RequestBody OrderUpdationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(request))
                .build();
    }

    @PatchMapping("/orders/update/status")
    public ApiResponse<OrderResponse> updateOrderStatus(@RequestParam Integer id, @RequestParam OrderStatus status) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrderStatus(id, status))
                .build();
    }

    @PatchMapping("/orders/update/driver-username")
    public ApiResponse<OrderResponse> updateOrderDriver(@RequestParam Integer id, @RequestParam(name = "driver-username") String driverUsername) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrderDriver(id, driverUsername))
                .build();
    }

    @PatchMapping("/orders/update/driver-username-and-status")
    public ApiResponse<OrderResponse> updateOrderDriverAndStatus(@RequestParam Integer id, @RequestParam(name = "driver-username") String driverUsername, @RequestParam OrderStatus status) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrderDriverAndStatus(id, driverUsername, status))
                .build();
    }

    @DeleteMapping("/orders/delete/id")
    public ApiResponse<Boolean> deleteOrder(@RequestParam Integer id) {
        orderService.deleteOrder(id);
        return ApiResponse.<Boolean>builder()
                .message("Order deleted successfully")
                .result(true)
                .build();
    }
}
