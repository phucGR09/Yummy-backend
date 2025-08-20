package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.request.MenuItemCreationRequest;
import com.intro2se.yummy.dtos.request.MenuItemUpdationRequest;
import com.intro2se.yummy.dtos.response.MenuItemResponse;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.MenuItemRepository;
import com.intro2se.yummy.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class MenuItemService {
    MenuItemRepository menuItemRepository;
    RestaurantRepository restaurantRepository;

    public List<MenuItemResponse> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(MenuItemResponse::fromMenuItem)
                .toList();
    }

    public List<MenuItemResponse> getMenuItemsByRestaurantId(int id) {
        return menuItemRepository.findByRestaurantId(id).stream()
                .map(MenuItemResponse::fromMenuItem)
                .toList();
    }

    public List<MenuItemResponse> getMenuItemsContainingDishName(String name) {
        if (name.length() > 100) {
            throw new AppException(ErrorCode.DISH_NAME_TOO_LONG);
        }

        return menuItemRepository.findByNameContainingIgnoreCase(name).stream()
                .map(MenuItemResponse::fromMenuItem)
                .toList();
    }

    public MenuItemResponse createMenuItems(MenuItemCreationRequest request) {
        request.setRestaurant(restaurantRepository.findById(request.getRestaurantId()).orElseThrow(
                () -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND)
        ));

        return MenuItemResponse.fromMenuItem(menuItemRepository.save(request.toMenuItem()));
    }

    public MenuItemResponse updateMenuItem(MenuItemUpdationRequest request) {
        request.setRestaurant(restaurantRepository.findById(request.getRestaurantId()).orElseThrow(
                () -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND)
        ));

        return MenuItemResponse.fromMenuItem(menuItemRepository.save(request.toMenuItem()));
    }

    public void deleteMenuItem(int id) {
        menuItemRepository.deleteById(id);
    }
}
