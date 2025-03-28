package com.example.ITdeviceMarket.controller;

import java.util.List;
import com.example.ITdeviceMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.service.OrderService;

@Controller
public class OrderController {
    @Autowired OrderService orderService;
    @Autowired UserService userService;

}
