package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.request.RestaurantCreationRequest;
import com.intro2se.yummy.dtos.request.RestaurantUpdationRequest;
import com.intro2se.yummy.dtos.response.RestaurantResponse;
import com.intro2se.yummy.entities.Restaurant;
import com.intro2se.yummy.entities.RestaurantOwner;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.RestaurantOwnerRepository;
import com.intro2se.yummy.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantService {
    RestaurantRepository restaurantRepository;
    RestaurantOwnerRepository restaurantOwnerRepository;

    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantResponse::fromRestaurant)
                .toList();
    }

    public RestaurantResponse getRestaurantById(Integer id) {
        return RestaurantResponse.fromRestaurant(
                restaurantRepository.findById(id).orElseThrow(
                        () -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND)
                )
        );
    }

    public List<RestaurantResponse> getRestaurantByOwnerUsername(String username) {
        return restaurantRepository.findByOwnerUserUsername(username).stream()
                .map(RestaurantResponse::fromRestaurant)
                .toList();
    }

    public RestaurantResponse getRestaurantByName(String name) {
        return RestaurantResponse.fromRestaurant(
                restaurantRepository.findByName(name).orElseThrow(
                        () -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND)
                )
        );
    }

    public RestaurantResponse createRestaurant(RestaurantCreationRequest request) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findByUserUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.RESTAURANT_OWNER_NOT_FOUND)
        );
        request.setOwner(restaurantOwner);
        Restaurant restaurant = RestaurantCreationRequest.toRestaurant(request);

        return RestaurantResponse.fromRestaurant(restaurantRepository.save(restaurant));
    }

    public RestaurantResponse updateRestaurant(RestaurantUpdationRequest request) {
        RestaurantOwner owner = restaurantOwnerRepository.findByUserUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.RESTAURANT_OWNER_NOT_FOUND)
        );
        request.setOwner(owner);

        return RestaurantResponse.fromRestaurant(restaurantRepository.save(request.toRestaurant()));
    }

    public void deleteRestaurantById(Integer id) {
        restaurantRepository.deleteById(id);
    }
}
