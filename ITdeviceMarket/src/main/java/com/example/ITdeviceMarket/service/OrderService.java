package com.example.ITdeviceMarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.repository.OrderRepository;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order generateOrder(Order order) {
        double price = 0.0;
        if ("Android".equalsIgnoreCase(order.getDevice_type())) {
            price = 900.0; // Price for Android
        } else if ("iPhone".equalsIgnoreCase(order.getDevice_type())) {
            price = 950.0; // Price for iPhone
        }
        order.setTotal_price(price * order.getDevice_quantity());
        return orderRepository.save(order);
    }
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
