package com.example.ITdeviceMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ITdeviceMarket.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
        User findByEmail(String email);
}
