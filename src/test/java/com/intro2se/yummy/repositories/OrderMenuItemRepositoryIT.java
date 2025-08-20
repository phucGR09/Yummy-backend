package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.MenuItem;
import com.intro2se.yummy.entities.Order;
import com.intro2se.yummy.entities.OrderMenuItem;
import com.intro2se.yummy.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class OrderMenuItemRepositoryIT {

    @Autowired
    private OrderMenuItemRepository orderMenuItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    void findByOrderId_OrderWithMultipleItems_ReturnsAllOrderItems() {
        Order order = new Order();
        order.setStatus(OrderStatus.RESTAURANT_CONFIRMED);
        orderRepository.save(order);

        MenuItem item1 = new MenuItem();
        item1.setName("Pizza");
        item1.setPrice(BigDecimal.valueOf(12.0));
        menuItemRepository.save(item1);

        OrderMenuItem orderItem1 = new OrderMenuItem();
        orderItem1.setOrder(order);
        orderItem1.setMenuItem(item1);
        orderItem1.setQuantity(2);
        orderMenuItemRepository.save(orderItem1);

        List<OrderMenuItem> items = orderMenuItemRepository.findByOrderId(order.getId());
        assertThat(items).hasSize(1);
        assertThat(items.getFirst().getQuantity()).isEqualTo(2);
    }

    @Test
    void findByItemId_MultipleOrdersWithSameItem_ReturnsAllOrdersContainingItem() {
        MenuItem item = new MenuItem();
        item.setName("Burger");
        item.setPrice(BigDecimal.valueOf(10.0));
        menuItemRepository.save(item);

        Order order1 = new Order();
        order1.setStatus(OrderStatus.RESTAURANT_CONFIRMED);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setStatus(OrderStatus.RESTAURANT_CONFIRMED);
        orderRepository.save(order2);

        OrderMenuItem orderItem1 = new OrderMenuItem();
        orderItem1.setOrder(order1);
        orderItem1.setMenuItem(item);
        orderItem1.setQuantity(1);
        orderMenuItemRepository.save(orderItem1);

        OrderMenuItem orderItem2 = new OrderMenuItem();
        orderItem2.setOrder(order2);
        orderItem2.setMenuItem(item);
        orderItem2.setQuantity(2);
        orderMenuItemRepository.save(orderItem2);

        List<OrderMenuItem> items = orderMenuItemRepository.findByMenuItemId(item.getId());
        assertThat(items).hasSize(2);
        assertThat(items).extracting(OrderMenuItem::getQuantity)
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void findByQuantityGreaterThan_OrdersWithLargeQuantities_ReturnsMatchingOrders() {
        MenuItem item = new MenuItem();
        item.setName("Pizza");
        item.setPrice(BigDecimal.valueOf(15.0));
        menuItemRepository.save(item);

        Order order = new Order();
        orderRepository.save(order);

        OrderMenuItem largeOrder = new OrderMenuItem();
        largeOrder.setOrder(order);
        largeOrder.setMenuItem(item);
        largeOrder.setQuantity(5);
        orderMenuItemRepository.save(largeOrder);

        OrderMenuItem smallOrder = new OrderMenuItem();
        smallOrder.setOrder(order);
        smallOrder.setMenuItem(item);
        smallOrder.setQuantity(2);
        orderMenuItemRepository.save(smallOrder);

        List<OrderMenuItem> largeOrders = orderMenuItemRepository.findByQuantityGreaterThan(3);
        assertThat(largeOrders).hasSize(1);
        assertThat(largeOrders.getFirst().getQuantity()).isEqualTo(5);
    }

    @Test
    void deleteByOrderId_ExistingOrder_RemovesAllOrderItems() {
        Order order = new Order();
        orderRepository.save(order);

        MenuItem item = new MenuItem();
        item.setName("Salad");
        item.setPrice(BigDecimal.valueOf(8.0));
        menuItemRepository.save(item);

        OrderMenuItem orderItem = new OrderMenuItem();
        orderItem.setOrder(order);
        orderItem.setMenuItem(item);
        orderItem.setQuantity(1);
        orderMenuItemRepository.save(orderItem);

        orderMenuItemRepository.deleteByOrderId(order.getId());
        List<OrderMenuItem> remainingItems = orderMenuItemRepository.findByOrderId(order.getId());
        assertThat(remainingItems).isEmpty();
    }
}