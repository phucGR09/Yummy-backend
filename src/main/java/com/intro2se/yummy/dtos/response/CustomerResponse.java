package com.intro2se.yummy.dtos.response;

import com.intro2se.yummy.entities.Customer;
import com.intro2se.yummy.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CustomerResponse {
    User user;
    String address;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setUser(user);
        customer.setAddress(address);
        return customer;
    }

    public static CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getUser(),
                customer.getAddress()
        );
    }
}
