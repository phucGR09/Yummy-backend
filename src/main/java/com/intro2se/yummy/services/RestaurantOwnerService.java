package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.response.RestaurantOwnerResponse;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.RestaurantOwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantOwnerService {
    RestaurantOwnerRepository restaurantOwnerRepository;

    public List<RestaurantOwnerResponse> getAllRestaurantOwners() {
        return restaurantOwnerRepository.findAll().stream()
                .map(RestaurantOwnerResponse::fromRestaurantOwner)
                .toList();
    }

    public RestaurantOwnerResponse getRestaurantOwnerByUserName(String userName) {
        return RestaurantOwnerResponse.fromRestaurantOwner(
                restaurantOwnerRepository.findByUserUsername(userName).orElseThrow(
                        () -> new AppException(ErrorCode.RESTAURANT_OWNER_NOT_FOUND)
                )
        );
    }

    public RestaurantOwnerResponse getRestaurantOwnerByEmail(String email) {
        return RestaurantOwnerResponse.fromRestaurantOwner(
                restaurantOwnerRepository.findByUserEmail(email).orElseThrow(
                        () -> new AppException(ErrorCode.EMAIL_NOT_FOUND)
                )
        );
    }

    public RestaurantOwnerResponse getRestaurantOwnerById(int id) {
        return RestaurantOwnerResponse.fromRestaurantOwner(
                restaurantOwnerRepository.findById(id).orElseThrow(
                        () -> new AppException(ErrorCode.RESTAURANT_OWNER_NOT_FOUND)
                )
        );
    }
}
