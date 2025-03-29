package com.example.ITdeviceMarket.controller;

import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.repository.OrderRepository;
import com.example.ITdeviceMarket.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Home page
    @GetMapping("/")
    public String home() {
        return "index";  // Default home page
    }

    // Registration page
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String role) {

        if (userRepository.findByEmail(email) != null) {
            return "redirect:/register?error=Email already exists";
        }

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        userRepository.save(user);
        return "redirect:/login";
    }

    // Login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session) {

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", user.getEmail());
            session.setAttribute("role", user.getRole());
            return "redirect:/order";
        }

        return "redirect:/login?error=Invalid credentials";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Order page (User & Admin only)
    @GetMapping("/order")
    public String showOrderPage(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        return "order";
    }

    @PostMapping("/order")
    public String placeOrder(@RequestParam String device,
                             @RequestParam String color,
                             @RequestParam int quantity,
                             HttpSession session) {

        String username = (String) session.getAttribute("username");

        if (username == null || quantity <= 0) {
            return "redirect:/order?error=Invalid order";
        }

        double price = device.equalsIgnoreCase("android") ? 900 : 950;
        double totalAmount = price * quantity;

        Order order = new Order();
        order.setUsername(username);
        order.setDevice_type(device);
        order.setDevice_color(color);
        order.setDevice_quantity(quantity);
        order.setTotal_price(totalAmount);

        orderRepository.save(order);
        return "redirect:/receipt";
    }

    // Receipt page
    @GetMapping("/receipt")
    public String showReceipt(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderRepository.findByUsername(username);
        model.addAttribute("orders", orders);

        double totalAmount = orders.stream().mapToDouble(Order::getTotal_price).sum();
        model.addAttribute("totalAmount", totalAmount);

        return "receipt";
    }

    // Order history
    @GetMapping("/order-history")
    public String showOrderHistory(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderRepository.findByUsername(username);
        model.addAttribute("orders", orders);
        return "order-history";
    }

    // Admin page (Admin only)
    @GetMapping("/admin")
    public String showAdminDashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }

        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);

        double totalSales = orders.stream().mapToDouble(Order::getTotal_price).sum();
        model.addAttribute("totalSales", totalSales);

        return "admin";
    }
}

