package com.intro2se.yummy.dtos.response;

import com.intro2se.yummy.entities.DeliveryDriver;
import com.intro2se.yummy.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryDriverResponse {
    String avatarUrl;
    String licensePlate;
    String identityNumber;
    User user;

    public DeliveryDriver toDeliveryDriver() {
        DeliveryDriver deliveryDriver = new DeliveryDriver();
        deliveryDriver.setAvatarUrl(avatarUrl);
        deliveryDriver.setLicensePlate(licensePlate);
        deliveryDriver.setIdentityNumber(identityNumber);
        deliveryDriver.setUser(user);
        return deliveryDriver;
    }

    public static DeliveryDriverResponse fromDeliveryDriver(DeliveryDriver deliveryDriver) {
        return new DeliveryDriverResponse(
                deliveryDriver.getAvatarUrl(),
                deliveryDriver.getLicensePlate(),
                deliveryDriver.getIdentityNumber(),
                deliveryDriver.getUser()
        );
    }
}
