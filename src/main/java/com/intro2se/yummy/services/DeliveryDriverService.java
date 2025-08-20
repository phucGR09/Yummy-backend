package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.response.DeliveryDriverResponse;
import com.intro2se.yummy.entities.DeliveryDriver;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.DeliveryDriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryDriverService {
    DeliveryDriverRepository deliveryDriverRepository;

    public List<DeliveryDriverResponse> getAllDeliveryDrivers() {
        List<DeliveryDriver> deliveryDrivers = deliveryDriverRepository.findAll();

        return deliveryDrivers.stream().map(DeliveryDriverResponse::fromDeliveryDriver).toList();
    }

    public DeliveryDriverResponse getDeliveryDriverByUsername(String userName) {
        DeliveryDriver deliveryDriver = deliveryDriverRepository.findByUserUsername(userName).orElseThrow(
                () -> new AppException(ErrorCode.DELIVERY_DRIVER_NOT_FOUND)
        );
        return DeliveryDriverResponse.fromDeliveryDriver(deliveryDriver);
    }
}
