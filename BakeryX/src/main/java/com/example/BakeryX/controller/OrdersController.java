package com.example.BakeryX.controller;

import com.example.BakeryX.entity.Order;
import com.example.BakeryX.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrderServices orderServices;

    @Autowired
    public OrdersController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @PostMapping
    public void placeOrder(@RequestBody Order order) {
        orderServices.placeOrder(order.getCustomerName(), order.getItem(), order.getStatus(), order.getPickupTime());
    }

    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable String status) {
        return orderServices.getQueuedOrdersByStatus(status);
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderServices.getOrders();
    }

    @PutMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable int id, @RequestParam String status) {
        boolean updated = orderServices.updateOrderStatus(id, status);
        return updated ? "Order status updated successfully." : "Order not found.";
    }

    @PutMapping("/{id}")
    public String updateOrderStatus(@PathVariable int id, @RequestBody Order updatedOrder) {
        boolean updated = orderServices.updateOrderStatus(id, updatedOrder.getStatus());
        return updated ? "Order status updated successfully." : "Order not found.";
    }

    @GetMapping("/sorted")
    public List<Order> getSortedOrdersByPickupTime() {
        return orderServices.getOrdersSortedByPickupTime();
    }
}