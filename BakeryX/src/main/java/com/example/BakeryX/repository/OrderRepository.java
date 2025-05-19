package com.example.BakeryX.repository;

import com.example.BakeryX.entity.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private final File file = new File("orders.json");
    private final ObjectMapper objectMapper;

    public OrderRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public List<Order> getAllOrders() {
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Order>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read orders", e);
        }
    }

    public void saveAllOrders(List<Order> orders) {
        try {
            objectMapper.writeValue(file, orders);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save orders", e);
        }
    }

    public void saveOrder(Order order) {
        List<Order> orders = getAllOrders();
        orders.add(order);
        saveAllOrders(orders);
    }

    public Optional<Order> findById(int id) {
        return getAllOrders().stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    public boolean updateOrderStatus(int id, String newStatus) {
        List<Order> orders = getAllOrders();
        for (Order order : orders) {
            if (order.getId() == id) {
                order.setStatus(newStatus);
                saveAllOrders(orders);
                return true;
            }
        }
        return false;
    }
}
