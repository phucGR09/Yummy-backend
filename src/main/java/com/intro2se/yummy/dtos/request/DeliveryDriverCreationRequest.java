package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.DeliveryDriver;
import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.DeliveryDriverRepository;
import com.intro2se.yummy.repositories.UserRepository;
import com.intro2se.yummy.services.UserService;
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
public class DeliveryDriverCreationRequest {
    @JsonIgnore
    User user;

    @NotBlank(message = "INVALID_AVATAR_URL")
    String avatarUrl;

    @NotBlank(message = "INVALID_LICENSE_PLATE")
    String licensePlate;

    @NotBlank(message = "INVALID_IDENTITY_NUMBER")
    String identityNumber;

    @NotBlank(message = "INVALID_USER_NAME")
    String username;

    public DeliveryDriver toDeliveryDriver() {
        DeliveryDriver deliveryDriver = new DeliveryDriver();
        deliveryDriver.setAvatarUrl(avatarUrl);
        deliveryDriver.setLicensePlate(licensePlate);
        deliveryDriver.setIdentityNumber(identityNumber);

        if (user == null) {
            throw new AppException(ErrorCode.NULL_USER);
        }
        deliveryDriver.setUser(user);

        return deliveryDriver;
    }
}
