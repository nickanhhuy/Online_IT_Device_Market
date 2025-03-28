package com.example.ITdeviceMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.model.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUser(User user);
    // Find the last order by a specific user
    Order findLastOrderByUserId(User user);
}
