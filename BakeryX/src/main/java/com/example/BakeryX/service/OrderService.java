package com.example.BakeryX.service;


import com.example.BakeryX.entity.CustomOrder;

import java.util.List;

public interface OrderService {
    CustomOrder createOrder(CustomOrder order, String userType);
    List<CustomOrder> getAllOrders();
    CustomOrder updateOrder(Long id, CustomOrder updatedOrder);
    boolean deleteOrder(Long id);
}
