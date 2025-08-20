package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.RestaurantOwner;
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
public class RestaurantOwnerRepositoryIT {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findById_WithExistingOwner_ReturnsCorrectOwner() {
        // Arrange
        User user = new User();
        user.setUsername("owner_john");
        user.setPassword("password123");
        user.setEmail("owner.john@example.com");
        user.setFullName("John Owner");
        user.setPhoneNumber("123-456-7890");
        user.setUserType(UserType.RESTAURANT_OWNER);
        userRepository.save(user);

        RestaurantOwner owner = new RestaurantOwner();
        owner.setUser(user);
        owner.setTaxNumber("TAX123456");
        restaurantOwnerRepository.save(owner);

        // Act
        Optional<RestaurantOwner> foundOwner = restaurantOwnerRepository.findById(owner.getId());

        // Assert
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get().getTaxNumber()).isEqualTo("TAX123456");
        assertThat(foundOwner.get().getUser().getUsername()).isEqualTo("owner_john");
    }

    @Test
    void findByTaxNumber_WithExistingTaxNumber_ReturnsCorrectOwner() {
        // Arrange
        User user = new User();
        user.setUsername("owner_jane");
        user.setPassword("password456");
        user.setEmail("owner.jane@example.com");
        user.setFullName("Jane Owner");
        user.setPhoneNumber("987-654-3210");
        user.setUserType(UserType.RESTAURANT_OWNER);
        userRepository.save(user);

        RestaurantOwner owner = new RestaurantOwner();
        owner.setUser(user);
        owner.setTaxNumber("TAX789012");
        restaurantOwnerRepository.save(owner);

        // Act
        Optional<RestaurantOwner> foundOwner = restaurantOwnerRepository.findByTaxNumber("TAX789012");

        // Assert
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get().getUser().getUsername()).isEqualTo("owner_jane");
    }

    @Test
    void save_WithValidOwner_SavesSuccessfully() {
        // Arrange
        User user = new User();
        user.setUsername("owner_bob");
        user.setPassword("password789");
        user.setEmail("owner.bob@example.com");
        user.setFullName("Bob Owner");
        user.setPhoneNumber("555-555-5555");
        user.setUserType(UserType.RESTAURANT_OWNER);
        userRepository.save(user);

        RestaurantOwner owner = new RestaurantOwner();
        owner.setUser(user);
        owner.setTaxNumber("TAX345678");

        // Act
        RestaurantOwner savedOwner = restaurantOwnerRepository.save(owner);

        // Assert
        assertThat(savedOwner.getId()).isNotNull();
        assertThat(savedOwner.getTaxNumber()).isEqualTo("TAX345678");
        assertThat(savedOwner.getUser().getUsername()).isEqualTo("owner_bob");
    }

    @Test
    void findAll_WithMultipleOwners_ReturnsAllOwners() {
        // Arrange
        // First owner
        User user1 = new User();
        user1.setUsername("owner_alice");
        user1.setPassword("password111");
        user1.setEmail("owner.alice@example.com");
        user1.setUserType(UserType.RESTAURANT_OWNER);
        userRepository.save(user1);

        RestaurantOwner owner1 = new RestaurantOwner();
        owner1.setUser(user1);
        owner1.setTaxNumber("TAX111111");
        restaurantOwnerRepository.save(owner1);

        // Second owner
        User user2 = new User();
        user2.setUsername("owner_charlie");
        user2.setPassword("password222");
        user2.setEmail("owner.charlie@example.com");
        user2.setUserType(UserType.RESTAURANT_OWNER);
        userRepository.save(user2);

        RestaurantOwner owner2 = new RestaurantOwner();
        owner2.setUser(user2);
        owner2.setTaxNumber("TAX222222");
        restaurantOwnerRepository.save(owner2);

        // Act
        List<RestaurantOwner> allOwners = restaurantOwnerRepository.findAll();

        // Assert
        assertThat(allOwners).hasSize(2);
        assertThat(allOwners).extracting(RestaurantOwner::getTaxNumber)
                .containsExactlyInAnyOrder("TAX111111", "TAX222222");
    }

    @Test
    void delete_ExistingOwner_RemovesOwner() {
        // Arrange
        User user = new User();
        user.setUsername("owner_david");
        user.setPassword("password333");
        user.setEmail("owner.david@example.com");
        user.setUserType(UserType.RESTAURANT_OWNER);
        userRepository.save(user);

        RestaurantOwner owner = new RestaurantOwner();
        owner.setUser(user);
        owner.setTaxNumber("TAX333333");
        restaurantOwnerRepository.save(owner);

        // Act
        restaurantOwnerRepository.delete(owner);
        Optional<RestaurantOwner> deletedOwner = restaurantOwnerRepository.findById(owner.getId());

        // Assert
        assertThat(deletedOwner).isEmpty();
    }

    @Test
    void findByUserEmail_WithExistingEmail_ReturnsCorrectOwner() {
        // Arrange
        User user = new User();
        user.setUsername("owner_eve");
        user.setPassword("password444");
        user.setEmail("owner.eve@example.com");
        user.setUserType(UserType.RESTAURANT_OWNER);
        userRepository.save(user);

        RestaurantOwner owner = new RestaurantOwner();
        owner.setUser(user);
        owner.setTaxNumber("TAX444444");
        restaurantOwnerRepository.save(owner);

        // Act
        Optional<RestaurantOwner> foundOwner = restaurantOwnerRepository.findByUserEmail("owner.eve@example.com");

        // Assert
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get().getTaxNumber()).isEqualTo("TAX444444");
        assertThat(foundOwner.get().getUser().getUsername()).isEqualTo("owner_eve");
    }
}