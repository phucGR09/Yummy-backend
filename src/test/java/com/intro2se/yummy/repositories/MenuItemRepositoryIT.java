package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.*;
import com.intro2se.yummy.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class MenuItemRepositoryIT {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantOwnerRepository ownerRepository;

    private Restaurant testRestaurant;

    @BeforeEach
    void setUp() {
        // Create a restaurant owner
        User ownerUser = new User();
        ownerUser.setUsername("test_owner");
        ownerUser.setPassword("password123");
        ownerUser.setEmail("owner@test.com");
        ownerUser.setUserType(UserType.RESTAURANT_OWNER);
        userRepository.save(ownerUser);

        RestaurantOwner owner = new RestaurantOwner();
        owner.setUser(ownerUser);
        owner.setTaxNumber("TAX123");
        ownerRepository.save(owner);

        // Create a restaurant
        testRestaurant = new Restaurant();
        testRestaurant.setName("Test Restaurant");
        testRestaurant.setOwner(owner);
        restaurantRepository.save(testRestaurant);
    }

    @Test
    void findById_WithExistingMenuItem_ReturnsCorrectMenuItem() {
        // Arrange
        MenuItem menuItem = createMenuItem("Burger", "Delicious burger", new BigDecimal("10.99"));
        menuItemRepository.save(menuItem);

        // Act
        Optional<MenuItem> foundMenuItem = menuItemRepository.findById(menuItem.getId());

        // Assert
        assertThat(foundMenuItem).isPresent();
        assertThat(foundMenuItem.get().getName()).isEqualTo("Burger");
        assertThat(foundMenuItem.get().getPrice()).isEqualByComparingTo(new BigDecimal("10.99"));
    }

    @Test
    void save_WithNullName_ThrowsException() {
        // Arrange
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(new BigDecimal("15.99"));
        menuItem.setRestaurant(testRestaurant);

        // Act & Assert
        assertThatThrownBy(() -> menuItemRepository.save(menuItem))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void save_WithNullPrice_ThrowsException() {
        // Arrange
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Pizza");
        menuItem.setRestaurant(testRestaurant);

        // Act & Assert
        assertThatThrownBy(() -> menuItemRepository.save(menuItem))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void findByRestaurantId_WithMultipleItems_ReturnsCorrectItems() {
        // Arrange
        MenuItem item1 = createMenuItem("Pizza", "Cheese pizza", new BigDecimal("12.99"));
        MenuItem item2 = createMenuItem("Pasta", "Spaghetti", new BigDecimal("9.99"));
        menuItemRepository.saveAll(List.of(item1, item2));

        // Act
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(testRestaurant.getId());

        // Assert
        assertThat(menuItems).hasSize(2);
        assertThat(menuItems).extracting(MenuItem::getName)
                .containsExactlyInAnyOrder("Pizza", "Pasta");
    }

    @Test
    void findByPriceLessThan_WithValidPrice_ReturnsCorrectItems() {
        // Arrange
        MenuItem item1 = createMenuItem("Salad", "Fresh salad", new BigDecimal("7.99"));
        MenuItem item2 = createMenuItem("Steak", "Premium steak", new BigDecimal("25.99"));
        menuItemRepository.saveAll(List.of(item1, item2));

        // Act
        List<MenuItem> affordableItems = menuItemRepository.findByPriceLessThan(new BigDecimal("10.00"));

        // Assert
        assertThat(affordableItems).hasSize(1);
        assertThat(affordableItems.getFirst().getName()).isEqualTo("Salad");
    }

    @Test
    void updateMenuItem_WithValidData_UpdatesSuccessfully() {
        // Arrange
        MenuItem menuItem = createMenuItem("Sandwich", "Classic sandwich", new BigDecimal("8.99"));
        menuItemRepository.save(menuItem);

        // Act
        menuItem.setName("Deluxe Sandwich");
        menuItem.setPrice(new BigDecimal("9.99"));
        menuItem.setDescription("Premium deluxe sandwich");
        MenuItem updatedItem = menuItemRepository.save(menuItem);

        // Assert
        assertThat(updatedItem.getName()).isEqualTo("Deluxe Sandwich");
        assertThat(updatedItem.getPrice()).isEqualByComparingTo(new BigDecimal("9.99"));
        assertThat(updatedItem.getDescription()).isEqualTo("Premium deluxe sandwich");
    }

    @Test
    void findByNameContaining_WithPartialName_ReturnsMatchingItems() {
        // Arrange
        MenuItem item1 = createMenuItem("Chicken Burger", "Classic chicken", new BigDecimal("11.99"));
        MenuItem item2 = createMenuItem("Beef Burger", "Classic beef", new BigDecimal("12.99"));
        MenuItem item3 = createMenuItem("Pizza", "Classic pizza", new BigDecimal("13.99"));
        menuItemRepository.saveAll(List.of(item1, item2, item3));

        // Act
        List<MenuItem> burgerItems = menuItemRepository.findByNameContaining("Burger");

        // Assert
        assertThat(burgerItems).hasSize(2);
        assertThat(burgerItems).extracting(MenuItem::getName)
                .containsExactlyInAnyOrder("Chicken Burger", "Beef Burger");
    }

    private MenuItem createMenuItem(String name, String description, BigDecimal price) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setImageUrl("https://example.com/images/" + name.toLowerCase().replace(" ", "-") + ".jpg");
        menuItem.setRestaurant(testRestaurant);
        return menuItem;
    }
}