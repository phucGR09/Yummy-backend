package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.request.OrderCreationRequest;
import com.intro2se.yummy.dtos.request.OrderUpdationRequest;
import com.intro2se.yummy.dtos.response.OrderResponse;
import com.intro2se.yummy.entities.Order;
import com.intro2se.yummy.enums.OrderStatus;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.CustomerRepository;
import com.intro2se.yummy.repositories.DeliveryDriverRepository;
import com.intro2se.yummy.repositories.OrderRepository;
import com.intro2se.yummy.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    RestaurantRepository restaurantRepository;
    DeliveryDriverRepository deliveryDriverRepository;
    CustomerRepository customerRepository;

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public List<OrderResponse> getOrdersByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public List<OrderResponse> getOrdersByCustomerUsername(String username) {
        return orderRepository.findByCustomerUserUsername(username).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public List<OrderResponse> getOrdersByRestaurantId(int restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public List<OrderResponse> getOrdersByDriverId(int driverId) {
        return orderRepository.findByDriverId(driverId).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public List<OrderResponse> getOrdersByDriverUsername(String driverUsername) {
        return orderRepository.findByDriverUserUsername(driverUsername).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public List<OrderResponse> getOrdersByCustomerUsernameAndStatus(String username, OrderStatus status) {
        return orderRepository.findByCustomerUserUsernameAndStatus(username, status).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public List<OrderResponse> getOrdersByRestaurantIdAndStatus(int restaurantId, OrderStatus status) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, status).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    public OrderResponse createOrder(OrderCreationRequest request) {
        request.setRestaurant(restaurantRepository.findById(request.getRestaurantId()).orElseThrow(
                () -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND)
        ));

        request.setCustomer(customerRepository.findByUserUsername(request.getCustomerUsername()).orElseThrow(
                () -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND)
        ));

        request.setDriver(deliveryDriverRepository.findByUserUsername(request.getDriverUsername()).orElse(null));

        return OrderResponse.fromOrder(orderRepository.save(request.toOrder()));
    }

    public OrderResponse updateOrder(OrderUpdationRequest request) {
        Order olderOrder = orderRepository.findById(request.getId()).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        );

        request.setRestaurant(restaurantRepository.findById(request.getRestaurantId()).orElseThrow(
                () -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND)
        ));

        request.setCustomer(customerRepository.findByUserUsername(request.getCustomerUsername()).orElseThrow(
                () -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND)
        ));

        if (olderOrder.getDriver() != null && !request.getDriverUsername().equals(olderOrder.getDriver().getUser().getUsername()))
            throw new AppException(ErrorCode.REASSIGN_DRIVER_IN_ORDER);
        request.setDriver(deliveryDriverRepository.findByUserUsername(request.getDriverUsername()).orElse(null));

        return OrderResponse.fromOrder(orderRepository.save(request.toOrder()));
    }

    public OrderResponse updateOrderStatus(int id, OrderStatus status) {
        OrderResponse orderResponse = OrderResponse.fromOrder(orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        ));
        orderResponse.setStatus(status);
        return OrderResponse.fromOrder(orderRepository.save(orderResponse.toOrder()));
    }

    public OrderResponse updateOrderDriver(int id, String driverUsername) {
        OrderResponse orderResponse = OrderResponse.fromOrder(orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        ));

        if (orderResponse.getDriver() != null) {
            throw new AppException(ErrorCode.REASSIGN_DRIVER_IN_ORDER);
        }

        orderResponse.setDriver(deliveryDriverRepository.findByUserUsername(driverUsername).orElseThrow(
                () -> new AppException(ErrorCode.DRIVER_NOT_FOUND)
        ));
        return OrderResponse.fromOrder(orderRepository.save(orderResponse.toOrder()));
    }

    public OrderResponse updateOrderDriverAndStatus(int id, String driverUsername, OrderStatus status) {
        OrderResponse orderResponse = OrderResponse.fromOrder(orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        ));

        if (orderResponse.getDriver() != null) {
            throw new AppException(ErrorCode.REASSIGN_DRIVER_IN_ORDER);
        }

        orderResponse.setDriver(deliveryDriverRepository.findByUserUsername(driverUsername).orElseThrow(
                () -> new AppException(ErrorCode.DRIVER_NOT_FOUND)
        ));
        orderResponse.setStatus(status);
        return OrderResponse.fromOrder(orderRepository.save(orderResponse.toOrder()));
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
