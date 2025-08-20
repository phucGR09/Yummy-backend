package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.request.CustomerUpdateRequest;
import com.intro2se.yummy.dtos.response.CustomerResponse;
import com.intro2se.yummy.entities.Customer;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService {
    CustomerRepository customerRepository;

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(CustomerResponse::fromCustomer).toList();
    }

    public CustomerResponse getCustomerByUsername(String userName) {
        Customer customer = customerRepository.findByUserUsername(userName).orElseThrow(
                () -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND)
        );
        return CustomerResponse.fromCustomer(customer);
    }

    public CustomerResponse updateCustomerByUsername(String username, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findByUserUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND)
        );
        return CustomerResponse.fromCustomer(customerRepository.save(request.mapToCustomer(customer)));
    }
}
