package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.RestaurantOwner;
import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.services.UserService;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantOwnerCreationRequest {
    @JsonIgnore
    User user;

    @NotBlank(message = "INVALID_TAX_NUMBER")
    private String taxNumber;

    @NotBlank(message = "INVALID_USER_NAME")
    private String username;

    public RestaurantOwner toRestaurantOwner() {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setTaxNumber(taxNumber);

        if (user == null) {
            throw new AppException(ErrorCode.NULL_USER);
        }
        restaurantOwner.setUser(user);

        return restaurantOwner;
    }
}
