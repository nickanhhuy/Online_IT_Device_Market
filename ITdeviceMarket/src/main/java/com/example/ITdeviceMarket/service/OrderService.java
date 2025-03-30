package com.example.ITdeviceMarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.repository.OrderRepository;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void generateOrder(Order order) {

        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByUsername(username);
    }

}


