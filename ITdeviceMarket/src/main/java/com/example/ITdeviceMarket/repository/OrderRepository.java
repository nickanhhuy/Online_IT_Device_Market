package com.example.ITdeviceMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ITdeviceMarket.model.Order;
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
