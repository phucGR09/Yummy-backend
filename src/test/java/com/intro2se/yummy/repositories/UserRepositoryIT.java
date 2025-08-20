package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.enums.UserType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findById_WithExistingUser_ReturnsCorrectUser() {
        // Arrange
        User user = createUser("john_doe", "john@example.com", "123-456-7890", UserType.CUSTOMER);
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findById(user.getId());

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("john_doe");
        assertThat(foundUser.get().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void save_WithDuplicateUsername_ThrowsException() {
        // Arrange
        User user1 = createUser("same_username", "john@example.com", "123-456-7890", UserType.CUSTOMER);
        userRepository.save(user1);

        User user2 = createUser("same_username", "jane@example.com", "987-654-3210", UserType.CUSTOMER);

        // Act & Assert
        assertThatThrownBy(() -> userRepository.save(user2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void save_WithDuplicatePhoneNumber_ThrowsException() {
        // Arrange
        User user1 = createUser("john_doe", "john@example.com", "same-phone", UserType.CUSTOMER);
        userRepository.save(user1);

        User user2 = createUser("jane_doe", "jane@example.com", "same-phone", UserType.CUSTOMER);

        // Act & Assert
        assertThatThrownBy(() -> userRepository.save(user2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void findByEmail_WithExistingEmail_ReturnsCorrectUser() {
        // Arrange
        User user = createUser("jane_doe", "jane@example.com", "123-456-7890", UserType.RESTAURANT_OWNER);
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("jane@example.com");

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("jane_doe");
        assertThat(foundUser.get().getUserType()).isEqualTo(UserType.RESTAURANT_OWNER);
    }

    @Test
    void findByPhoneNumber_WithExistingPhone_ReturnsCorrectUser() {
        // Arrange
        User user = createUser("bob_smith", "bob@example.com", "555-555-5555", UserType.DELIVERY_DRIVER);
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByPhoneNumber("555-555-5555");

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("bob_smith");
        assertThat(foundUser.get().getUserType()).isEqualTo(UserType.DELIVERY_DRIVER);
    }

    @Test
    void findByUserType_WithMultipleUsers_ReturnsCorrectUsers() {
        // Arrange
        User customer1 = createUser("customer1", "cust1@example.com", "111-111-1111", UserType.CUSTOMER);
        User customer2 = createUser("customer2", "cust2@example.com", "222-222-2222", UserType.CUSTOMER);
        User driver = createUser("driver1", "driver@example.com", "333-333-3333", UserType.DELIVERY_DRIVER);
        userRepository.saveAll(List.of(customer1, customer2, driver));

        // Act
        List<User> customers = userRepository.findByUserType(UserType.CUSTOMER);

        // Assert
        assertThat(customers).hasSize(2);
        assertThat(customers).extracting(User::getUsername)
                .containsExactlyInAnyOrder("customer1", "customer2");
    }

    @Test
    void saveUser_WithNullRequiredFields_ThrowsException() {
        // Arrange
        User invalidUser = new User();
        invalidUser.setFullName("Test User");
        invalidUser.setUserType(UserType.CUSTOMER);

        // Act & Assert
        assertThatThrownBy(() -> userRepository.save(invalidUser))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void updateUser_WithValidData_UpdatesSuccessfully() {
        // Arrange
        User user = createUser("alice_smith", "alice@example.com", "444-444-4444", UserType.CUSTOMER);
        userRepository.save(user);

        // Act
        user.setFullName("Alice Updated Smith");
        user.setEmail("alice.updated@example.com");
        User updatedUser = userRepository.save(user);

        // Assert
        assertThat(updatedUser.getFullName()).isEqualTo("Alice Updated Smith");
        assertThat(updatedUser.getEmail()).isEqualTo("alice.updated@example.com");
    }

    @Test
    void doesUserNameExist_WithExistingUserName_ReturnsTrue() {
        // Arrange
        User user = createUser("alice_smith", "alice.smith@gmail.com", "555-555-5555", UserType.CUSTOMER);
        userRepository.save(user);

        // Act & Assert
        assertThat(userRepository.existsByUsername("alice_smith")).isTrue();
    }

    private User createUser(String username, String email, String phone, UserType userType) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("password123");
        user.setEmail(email);
        user.setFullName(username + " Full Name");
        user.setPhoneNumber(phone);
        user.setUserType(userType);
        return user;
    }
}