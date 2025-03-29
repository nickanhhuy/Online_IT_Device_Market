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

    public Order generateOrder(Order order) {
        double price;
        switch (order.getDevice_type().toLowerCase()) {
            case "android":
                price = 900.0;
                break;
            case "iphone":
                price = 950.0;
                break;
            default:
                throw new IllegalArgumentException("Invalid device type");
        }

        order.setTotal_price(price * order.getDevice_quantity());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElse(null); // Return null if not found
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByUsername(username);
    }
}


