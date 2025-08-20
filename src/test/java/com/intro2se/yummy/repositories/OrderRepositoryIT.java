package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.*;
import com.intro2se.yummy.enums.OrderStatus;
import com.intro2se.yummy.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryDriverRepository driverRepository;

    @Test
    void findOrderByCustomerId_FindOrderWithTotalPriceOf100Dot5_ReturnsOrderWithTotalPriceOf100Dot5() {
        // Arrange: Create related entities
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("securepassword123");
        user.setEmail("john.doe@example.com");
        user.setFullName("John Doe");
        user.setPhoneNumber("123-456-7890");
        user.setUserType(UserType.CUSTOMER);

        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurantRepository.save(restaurant);

        Order order = new Order();
        order.setRestaurant(restaurant);
        order.setCustomer(customer);
        order.setOrderTime(LocalDateTime.now());
        order.setTotalPrice(new BigDecimal("100.50"));
        order.setStatus(OrderStatus.WAITING_RESTAURANT_CONFIRMATION);

        // Act: Save the order
        orderRepository.save(order);

        // Assert: Verify the order can be retrieved
        List<Order> orders = orderRepository.findByCustomerId(customer.getId());
        assertThat(orders).hasSize(1);
        assertThat(orders.getFirst().getTotalPrice()).isEqualByComparingTo("100.50");
    }

    @Test
    void findOrderByRestaurantId_WithMultipleOrders_ReturnsCorrectOrders() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurantRepository.save(restaurant);

        // Create two orders for the restaurant
        Order order1 = new Order();
        order1.setRestaurant(restaurant);
        order1.setOrderTime(LocalDateTime.now());
        order1.setTotalPrice(new BigDecimal("50.00"));
        order1.setStatus(OrderStatus.RESTAURANT_CONFIRMED);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setRestaurant(restaurant);
        order2.setOrderTime(LocalDateTime.now());
        order2.setTotalPrice(new BigDecimal("75.00"));
        order2.setStatus(OrderStatus.WAITING_DELIVERY);
        orderRepository.save(order2);

        // Act
        List<Order> foundOrders = orderRepository.findByRestaurantId(restaurant.getId());

        // Assert
        assertThat(foundOrders).hasSize(2);
        assertThat(foundOrders).extracting(Order::getTotalPrice)
                .containsExactlyInAnyOrder(new BigDecimal("50.00"), new BigDecimal("75.00"));
    }

    @Test
    void findOrderByDriverId_WithAssignedOrders_ReturnsCorrectOrders() {
        // Arrange
        User driverUser = new User();
        driverUser.setUsername("driver");
        driverUser.setPassword("password");
        driverUser.setEmail("driver@test.com");
        driverUser.setUserType(UserType.DELIVERY_DRIVER);

        DeliveryDriver driver = new DeliveryDriver();
        driver.setUser(driverUser);
        driver.setLicensePlate("ABC123");
        driverRepository.save(driver);

        Order order = new Order();
        order.setDriver(driver);
        order.setOrderTime(LocalDateTime.now());
        order.setTotalPrice(new BigDecimal("30.00"));
        order.setStatus(OrderStatus.DELIVERING);
        orderRepository.save(order);

        // Act
        List<Order> foundOrders = orderRepository.findByDriverId(driver.getId());

        // Assert
        assertThat(foundOrders).hasSize(1);
        assertThat(foundOrders.getFirst().getDriver().getId()).isEqualTo(driver.getId());
        assertThat(foundOrders.getFirst().getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @Test
    void findOrdersByStatus_WithMultipleStatuses_ReturnsCorrectOrders() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurantRepository.save(restaurant);

        Order waitingOrder = new Order();
        waitingOrder.setRestaurant(restaurant);
        waitingOrder.setOrderTime(LocalDateTime.now());
        waitingOrder.setStatus(OrderStatus.WAITING_RESTAURANT_CONFIRMATION);
        orderRepository.save(waitingOrder);

        Order confirmedOrder = new Order();
        confirmedOrder.setRestaurant(restaurant);
        confirmedOrder.setOrderTime(LocalDateTime.now());
        confirmedOrder.setStatus(OrderStatus.RESTAURANT_CONFIRMED);
        orderRepository.save(confirmedOrder);

        // Act
        List<Order> waitingOrders = orderRepository.findByStatus(OrderStatus.WAITING_RESTAURANT_CONFIRMATION);
        List<Order> confirmedOrders = orderRepository.findByStatus(OrderStatus.RESTAURANT_CONFIRMED);

        // Assert
        assertThat(waitingOrders).hasSize(1);
        assertThat(confirmedOrders).hasSize(1);
        assertThat(waitingOrders.getFirst().getStatus()).isEqualTo(OrderStatus.WAITING_RESTAURANT_CONFIRMATION);
        assertThat(confirmedOrders.getFirst().getStatus()).isEqualTo(OrderStatus.RESTAURANT_CONFIRMED);
    }

    @Test
    void findOrdersByTimeRange_WithinSpecifiedPeriod_ReturnsCorrectOrders() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurantRepository.save(restaurant);

        LocalDateTime startTime = LocalDateTime.now().minusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        Order order1 = new Order();
        order1.setRestaurant(restaurant);
        order1.setOrderTime(LocalDateTime.now());
        order1.setTotalPrice(new BigDecimal("25.00"));
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setRestaurant(restaurant);
        order2.setOrderTime(LocalDateTime.now().minusDays(1)); // Outside time range
        order2.setTotalPrice(new BigDecimal("35.00"));
        orderRepository.save(order2);

        // Act
        List<Order> ordersInRange = orderRepository.findByTimeRange(startTime, endTime);

        // Assert
        assertThat(ordersInRange).hasSize(1);
        assertThat(ordersInRange.getFirst().getOrderTime()).isBetween(startTime, endTime);
    }
}