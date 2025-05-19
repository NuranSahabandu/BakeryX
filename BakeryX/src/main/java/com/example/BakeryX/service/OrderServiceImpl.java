package com.example.BakeryX.service;


import com.example.BakeryX.entity.CustomOrder;
import com.example.BakeryX.repository.CakeData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final AtomicLong idCounter = new AtomicLong(CakeData.getCakeList().size());

    @Override
    public CustomOrder createOrder(CustomOrder order, String userType) {
        long id = idCounter.incrementAndGet();
        order.setId(id);
        order.setStatus("Pending");
        if ("premium".equalsIgnoreCase(userType)) {
            order.setMessage(order.getMessage() + " (Priority)");
        }
        CakeData.getCakeList().add(order);
        return order;
    }

    @Override
    public List<CustomOrder> getAllOrders() {
        return CakeData.getCakeList();
    }

    @Override
    public CustomOrder updateOrder(Long id, CustomOrder updatedOrder) {
        List<CustomOrder> cakeList = CakeData.getCakeList();

        for (int i = 0; i < cakeList.size(); i++) {
            CustomOrder existingOrder = cakeList.get(i);
            if (existingOrder.getId().equals(id)) {
                updatedOrder.setId(id);
                updatedOrder.setStatus("Pending");
                cakeList.set(i, updatedOrder);
                return updatedOrder;
            }
        }
        return null;
    }

    @Override
    public boolean deleteOrder(Long id) {
        List<CustomOrder> cakeList = CakeData.getCakeList();
        return cakeList.removeIf(order -> order.getId().equals(id));
    }
}