package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.Customer;
import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerCreationRequest {
    @JsonIgnore
    User user;

    @NotBlank(message = "INVALID_ADDRESS")
    String address;

    @NotBlank(message = "INVALID_USER_NAME")
    String username;

    public Customer toCustomer() {
        Customer customer = new Customer();

        if (user == null) {
            throw new AppException(ErrorCode.NULL_USER);
        }
        customer.setUser(user);
        customer.setAddress(address);

        return customer;
    }
}
