package com.intro2se.yummy.dtos.response;

import com.intro2se.yummy.entities.RestaurantOwner;
import com.intro2se.yummy.entities.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantOwnerResponse {
    String taxNumber;
    User user;

    public RestaurantOwner toRestaurantOwner() {
        RestaurantOwner restaurantOwner = new RestaurantOwner();

        restaurantOwner.setTaxNumber(taxNumber);
        restaurantOwner.setUser(user);

        return restaurantOwner;
    }

    public static RestaurantOwnerResponse fromRestaurantOwner(RestaurantOwner restaurantOwner) {
        return new RestaurantOwnerResponse(
                restaurantOwner.getTaxNumber(),
                restaurantOwner.getUser()
        );
    }
}
