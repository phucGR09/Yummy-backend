package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.DeliveryDriver;
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
public class DeliveryDriverRepositoryIT {

    @Autowired
    private DeliveryDriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findById_WithExistingDriver_ReturnsCorrectDriver() {
        // Arrange
        User user = createUser("driver_john", "driver.john@example.com", "123-456-0001", UserType.DELIVERY_DRIVER);
        DeliveryDriver driver = createDriver(user, "ABC123", "ID00123");
        driverRepository.save(driver);

        // Act
        Optional<DeliveryDriver> foundDriver = driverRepository.findById(driver.getId());

        // Assert
        assertThat(foundDriver).isPresent();
        assertThat(foundDriver.get().getLicensePlate()).isEqualTo("ABC123");
        assertThat(foundDriver.get().getIdentityNumber()).isEqualTo("ID00123");
        assertThat(foundDriver.get().getUser().getUsername()).isEqualTo("driver_john");
    }

    @Test
    void findByLicensePlate_WithExistingPlate_ReturnsCorrectDriver() {
        // Arrange
        User user = createUser("driver_jane", "driver.jane@example.com", "123-456-0002", UserType.DELIVERY_DRIVER);
        DeliveryDriver driver = createDriver(user, "XYZ456", "ID00456");
        driverRepository.save(driver);

        // Act
        Optional<DeliveryDriver> foundDriver = driverRepository.findByLicensePlate("XYZ456");

        // Assert
        assertThat(foundDriver).isPresent();
        assertThat(foundDriver.get().getUser().getUsername()).isEqualTo("driver_jane");
        assertThat(foundDriver.get().getIdentityNumber()).isEqualTo("ID00456");
    }

    @Test
    void findByIdentityNumber_WithExistingId_ReturnsCorrectDriver() {
        // Arrange
        User user = createUser("driver_bob", "driver.bob@example.com", "123-456-0003", UserType.DELIVERY_DRIVER);
        DeliveryDriver driver = createDriver(user, "DEF789", "ID00789");
        driverRepository.save(driver);

        // Act
        Optional<DeliveryDriver> foundDriver = driverRepository.findByIdentityNumber("ID00789");

        // Assert
        assertThat(foundDriver).isPresent();
        assertThat(foundDriver.get().getLicensePlate()).isEqualTo("DEF789");
        assertThat(foundDriver.get().getUser().getUsername()).isEqualTo("driver_bob");
    }

    @Test
    void findAll_WithMultipleDrivers_ReturnsAllDrivers() {
        // Arrange
        User user1 = createUser("driver_alice", "driver.alice@example.com", "123-456-0004", UserType.DELIVERY_DRIVER);
        DeliveryDriver driver1 = createDriver(user1, "GHI012", "ID01012");
        driverRepository.save(driver1);

        User user2 = createUser("driver_charlie", "driver.charlie@example.com", "123-456-0005", UserType.DELIVERY_DRIVER);
        DeliveryDriver driver2 = createDriver(user2, "JKL345", "ID01345");
        driverRepository.save(driver2);

        // Act
        List<DeliveryDriver> allDrivers = driverRepository.findAll();

        // Assert
        assertThat(allDrivers).hasSize(2);
        assertThat(allDrivers).extracting(DeliveryDriver::getLicensePlate)
                .containsExactlyInAnyOrder("GHI012", "JKL345");
    }

    @Test
    void updateDriverAvatar_WithValidUrl_UpdatesSuccessfully() {
        // Arrange
        User user = createUser("driver_dave", "driver.dave@example.com", "123-456-0006", UserType.DELIVERY_DRIVER);
        DeliveryDriver driver = createDriver(user, "MNO678", "ID01678");
        driverRepository.save(driver);

        // Act
        driver.setAvatarUrl("https://example.com/new-avatar.jpg");
        DeliveryDriver updatedDriver = driverRepository.save(driver);

        // Assert
        assertThat(updatedDriver.getAvatarUrl()).isEqualTo("https://example.com/new-avatar.jpg");
        assertThat(updatedDriver.getLicensePlate()).isEqualTo("MNO678");
    }

    @Test
    void delete_ExistingDriver_RemovesDriver() {
        // Arrange
        User user = createUser("driver_eve", "driver.eve@example.com", "123-456-0007", UserType.DELIVERY_DRIVER);
        DeliveryDriver driver = createDriver(user, "PQR901", "ID01901");
        driverRepository.save(driver);

        // Act
        driverRepository.delete(driver);
        Optional<DeliveryDriver> deletedDriver = driverRepository.findById(driver.getId());

        // Assert
        assertThat(deletedDriver).isEmpty();
    }

    private User createUser(String username, String email, String phoneNumber, UserType userType) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("password123");
        user.setEmail(email);
        user.setFullName(username + " Full Name");
        user.setPhoneNumber(phoneNumber);
        user.setUserType(userType);
        return userRepository.save(user);
    }

    private DeliveryDriver createDriver(User user, String licensePlate, String identityNumber) {
        DeliveryDriver driver = new DeliveryDriver();
        driver.setUser(user);
        driver.setLicensePlate(licensePlate);
        driver.setIdentityNumber(identityNumber);
        driver.setAvatarUrl("https://example.com/avatar.jpg");
        return driver;
    }
}
