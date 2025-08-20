package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.Customer;
import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findById_WithExistingCustomer_ReturnsCorrectCustomer() {
        // Arrange
        User user = createUser("customer_john", "john@example.com", "John", "123-456-7891", UserType.CUSTOMER);
        Customer customer = createCustomer(user);
        customerRepository.save(customer);

        // Act
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getId());

        // Assert
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getUser().getUsername()).isEqualTo("customer_john");
        assertThat(foundCustomer.get().getUser().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void findByUserEmail_WithExistingEmail_ReturnsCorrectCustomer() {
        // Arrange
        User user = createUser("customer_jane", "jane@example.com", "Jane", "123-456-7892", UserType.CUSTOMER);
        Customer customer = createCustomer(user);
        customerRepository.save(customer);

        // Act
        Optional<Customer> foundCustomer = customerRepository.findByUserEmail("jane@example.com");

        // Assert
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getUser().getUsername()).isEqualTo("customer_jane");
    }

    @Test
    void findAll_WithMultipleCustomers_ReturnsAllCustomers() {
        // Arrange
        User user1 = createUser("customer_alice", "alice@example.com", "Alice", "123-456-7893", UserType.CUSTOMER);
        Customer customer1 = createCustomer(user1);
        customerRepository.save(customer1);

        User user2 = createUser("customer_bob", "bob@example.com", "Bob", "123-456-7894", UserType.CUSTOMER);
        Customer customer2 = createCustomer(user2);
        customerRepository.save(customer2);

        // Act
        List<Customer> allCustomers = customerRepository.findAll();

        // Assert
        assertThat(allCustomers).hasSize(2);
        assertThat(allCustomers).extracting(customer -> customer.getUser().getUsername())
                .containsExactlyInAnyOrder("customer_alice", "customer_bob");
    }

    @Test
    void save_WithValidCustomer_SavesSuccessfully() {
        // Arrange
        User user = createUser("customer_charlie", "charlie@example.com", "Charlie", "123-456-7895", UserType.CUSTOMER);
        Customer customer = createCustomer(user);

        // Act
        Customer savedCustomer = customerRepository.save(customer);

        // Assert
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getUser().getUsername()).isEqualTo("customer_charlie");
        assertThat(savedCustomer.getUser().getEmail()).isEqualTo("charlie@example.com");
    }

    @Test
    void delete_ExistingCustomer_RemovesCustomer() {
        // Arrange
        User user = createUser("customer_david", "david@example.com", "David", "123-456-7896", UserType.CUSTOMER);
        Customer customer = createCustomer(user);
        customerRepository.save(customer);

        // Act
        customerRepository.delete(customer);
        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getId());

        // Assert
        assertThat(deletedCustomer).isEmpty();
    }

    @Test
    void findByUserPhoneNumber_WithExistingPhone_ReturnsCorrectCustomer() {
        // Arrange
        User user = createUser("customer_eve", "eve@example.com", "Eve", "123-456-7897", UserType.CUSTOMER);
        userRepository.save(user);

        Customer customer = createCustomer(user);
        customerRepository.save(customer);

        // Act
        Optional<Customer> foundCustomer = customerRepository.findByUserPhoneNumber("123-456-7897");

        // Assert
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getUser().getUsername()).isEqualTo("customer_eve");
        assertThat(foundCustomer.get().getUser().getPhoneNumber()).isEqualTo("123-456-7897");
    }

    private User createUser(String username, String email, String fullName, String phoneNumber, UserType userType) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("password123");
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setUserType(userType);
        return userRepository.save(user);
    }

    private Customer createCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        return customer;
    }
}
