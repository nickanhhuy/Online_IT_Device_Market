package com.example.ITdeviceMarket.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
