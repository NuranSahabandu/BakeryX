package com.example.BakeryX.controller;


import com.example.BakeryX.entity.CustomOrder;
import com.example.BakeryX.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userType}")
    public CustomOrder createOrder(@RequestBody CustomOrder order, @PathVariable String userType) {
        return orderService.createOrder(order, userType);
    }

    @GetMapping
    public List<CustomOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public CustomOrder updateOrder(@PathVariable Long id, @RequestBody CustomOrder order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}

