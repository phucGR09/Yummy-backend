package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.request.OrderMenuItemCreationRequest;
import com.intro2se.yummy.dtos.request.OrderMenuItemUpdationRequest;
import com.intro2se.yummy.dtos.response.OrderMenuItemResponse;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.MenuItemRepository;
import com.intro2se.yummy.repositories.OrderMenuItemRepository;
import com.intro2se.yummy.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderMenuItemService {
    OrderMenuItemRepository orderMenuItemRepository;
    OrderRepository orderRepository;
    MenuItemRepository menuItemRepository;

    public List<OrderMenuItemResponse> getAllOrderMenuItems() {
        return orderMenuItemRepository.findAll().stream()
                .map(OrderMenuItemResponse::fromOrderMenuItem)
                .toList();
    }

    public List<OrderMenuItemResponse> getOrderMenuItemsByOrderId(int id) {
        return orderMenuItemRepository.findByOrderId(id).stream()
                .map(OrderMenuItemResponse::fromOrderMenuItem)
                .toList();
    }

    public List<OrderMenuItemResponse> getOrderMenuItemsByMenuItemId(int id) {
        return orderMenuItemRepository.findByMenuItemId(id).stream()
                .map(OrderMenuItemResponse::fromOrderMenuItem)
                .toList();
    }

    public OrderMenuItemResponse createOrderMenuItems(OrderMenuItemCreationRequest request) {
        request.setOrder(orderRepository.findById(request.getOrderId()).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        ));

        request.setMenuItem(menuItemRepository.findById(request.getMenuItemId()).orElseThrow(
                () -> new AppException(ErrorCode.MENU_ITEM_NOT_FOUND)
        ));

        return OrderMenuItemResponse.fromOrderMenuItem(orderMenuItemRepository.save(request.toOrderMenuItem()));
    }

    public OrderMenuItemResponse updateOrderMenuItem(OrderMenuItemUpdationRequest request) {
        request.setOrder(orderRepository.findById(request.getOrderId()).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        ));

        request.setMenuItem(menuItemRepository.findById(request.getMenuItemId()).orElseThrow(
                () -> new AppException(ErrorCode.MENU_ITEM_NOT_FOUND)
        ));

        return OrderMenuItemResponse.fromOrderMenuItem(orderMenuItemRepository.save(request.toOrderMenuItem()));
    }

    public void deleteOrderMenuItem(int id) {
        orderMenuItemRepository.deleteById(id);
    }
}
