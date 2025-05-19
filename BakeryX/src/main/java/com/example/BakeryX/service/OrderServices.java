package com.example.BakeryX.service;

import com.example.BakeryX.entity.Order;
import com.example.BakeryX.repository.OrderRepository;
import com.example.BakeryX.queue.SimpleQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderServices {

    private final OrderRepository orderRepository;
    private final AtomicInteger orderIdCounter = new AtomicInteger(0);

    @Autowired
    public OrderServices(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

        // Set counter to highest existing order ID
        List<Order> existingOrders = orderRepository.getAllOrders();
        existingOrders.stream()
                .mapToInt(Order::getId)
                .max()
                .ifPresent(orderIdCounter::set);
    }

    // Place new order with pickup time
    public void placeOrder(String customerName, String item, String status, LocalDateTime pickupTime) {
        int orderId = orderIdCounter.incrementAndGet();
        LocalDateTime now = LocalDateTime.now();

        Order order = new Order();
        order.setId(orderId);
        order.setCustomerName(customerName);
        order.setItem(item);
        order.setStatus(status);
        order.setTimestamp(now);
        order.setPickupTime(pickupTime);

        orderRepository.saveOrder(order);
    }

    // Return all orders
    public List<Order> getOrders() {
        return orderRepository.getAllOrders();
    }

    // Update the status of a specific order
    public boolean updateOrderStatus(int id, String newStatus) {
        return orderRepository.updateOrderStatus(id, newStatus);
    }

    /**
     * Returns orders filtered by status.
     * For "accepted" orders, the list is sorted by pickupTime using manual Bubble Sort.
     */
    public List<Order> getQueuedOrdersByStatus(String status) {
        List<Order> allOrders = orderRepository.getAllOrders();

        // Queue implementation to collect filtered orders
        SimpleQueue<Order> queue = new SimpleQueue<>();
        for (Order order : allOrders) {
            if (order.getStatus().equalsIgnoreCase(status)) {
                queue.enqueue(order);
            }
        }

        // Convert queue to list
        List<Order> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.dequeue());
        }

        // If status is "accepted", manually sort result by pickupTime using Bubble Sort
        if (status.equalsIgnoreCase("accepted")) {
            manualBubbleSortByPickupTime(result);
        }

        return result;
    }

    /**
     * Manually implemented Bubble Sort to sort orders by pickupTime (earlier times first).
     */
    private void manualBubbleSortByPickupTime(List<Order> orders) {
        int n = orders.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                LocalDateTime time1 = orders.get(j).getPickupTime();
                LocalDateTime time2 = orders.get(j + 1).getPickupTime();

                // Swap if pickupTime is not null and the current order is later than the next one
                if (time1 != null && time2 != null && time1.isAfter(time2)) {
                    Order temp = orders.get(j);
                    orders.set(j, orders.get(j + 1));
                    orders.set(j + 1, temp);
                }
            }
        }
    }

    // Optional reuse for external sorting call
    public List<Order> getOrdersSortedByPickupTime() {
        return getQueuedOrdersByStatus("accepted");
    }
}
