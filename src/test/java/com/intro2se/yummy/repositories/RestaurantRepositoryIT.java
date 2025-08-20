package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.Restaurant;
import com.intro2se.yummy.entities.RestaurantOwner;
import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RestaurantRepositoryIT {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantOwnerRepository ownerRepository;

    @Test
    void findRestaurantByName_ExistingRestaurant_ReturnsCorrectRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Tasty Burger");
        restaurant.setAddress("123 Main St");
        restaurant.setOpeningHours(LocalTime.of(9, 0));
        restaurantRepository.save(restaurant);

        Optional<Restaurant> found = restaurantRepository.findByName("Tasty Burger");
        assertThat(found).isPresent();
        assertThat(found.get()).isNotNull();
        assertThat(found.get().getName()).isEqualTo("Tasty Burger");
    }

    @Test
    void findRestaurantsByOwnerId_MultipleRestaurants_ReturnsCorrectRestaurants() {
        User ownerUser = new User();
        ownerUser.setUserType(UserType.RESTAURANT_OWNER);
        ownerUser.setUsername("owner");
        ownerUser.setEmail("owner@test.com");
        ownerUser.setPassword("password");

        RestaurantOwner owner = new RestaurantOwner();
        owner.setUser(ownerUser);
        owner.setTaxNumber("123456789");
        ownerRepository.save(owner);

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Restaurant 1");
        restaurant1.setOwner(owner);
        restaurantRepository.save(restaurant1);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Restaurant 2");
        restaurant2.setOwner(owner);
        restaurantRepository.save(restaurant2);

        List<Restaurant> ownerRestaurants = restaurantRepository.findByOwnerId(owner.getId());
        assertThat(ownerRestaurants).hasSize(2);
        assertThat(ownerRestaurants).extracting(Restaurant::getName)
                .containsExactlyInAnyOrder("Restaurant 1", "Restaurant 2");
    }

    @Test
    void findRestaurantsByAddressContaining_PartialMatch_ReturnsMatchingRestaurants() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Restaurant 1");
        restaurant1.setAddress("123 Oak Street");
        restaurantRepository.save(restaurant1);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Restaurant 2");
        restaurant2.setAddress("456 Oak Street");
        restaurantRepository.save(restaurant2);

        List<Restaurant> foundRestaurants = restaurantRepository.findByAddressContaining("Oak");
        assertThat(foundRestaurants).hasSize(2);
        assertThat(foundRestaurants).extracting(Restaurant::getAddress)
                .allMatch(address -> address.contains("Oak"));
    }

    @Test
    void findRestaurantsByOpeningHoursBefore_EarlyOpeningRestaurants_ReturnsEarlyOpeningRestaurants() {
        Restaurant earlyRestaurant = new Restaurant();
        earlyRestaurant.setName("Early Bird");
        earlyRestaurant.setOpeningHours(LocalTime.of(6, 0));
        restaurantRepository.save(earlyRestaurant);

        Restaurant lateRestaurant = new Restaurant();
        lateRestaurant.setName("Night Owl");
        lateRestaurant.setOpeningHours(LocalTime.of(11, 0));
        restaurantRepository.save(lateRestaurant);

        List<Restaurant> earlyOpenings = restaurantRepository.findByOpeningHoursBefore(LocalTime.of(8, 0));
        assertThat(earlyOpenings).hasSize(1);
        assertThat(earlyOpenings.getFirst().getName()).isEqualTo("Early Bird");
    }
}
