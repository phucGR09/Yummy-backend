package com.intro2se.yummy.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intro2se.yummy.entities.Customer;
import com.intro2se.yummy.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerUpdateRequest {
    @NotBlank(message = "INVALID_ADDRESS")
    String address;

    public Customer mapToCustomer(Customer customer) {
        customer.setAddress(address);
        return customer;
    }
}
