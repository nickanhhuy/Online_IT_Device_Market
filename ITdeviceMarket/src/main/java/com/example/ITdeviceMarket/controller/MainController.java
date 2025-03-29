package com.example.ITdeviceMarket.controller;

import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.repository.OrderRepository;
import com.example.ITdeviceMarket.repository.UserRepository;
import com.example.ITdeviceMarket.service.OrderService;
import com.example.ITdeviceMarket.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

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
                               @RequestParam String role,
                               Model model) {
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        userService.registerUser(user);
        return "redirect:/login";
    }

    // Login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String role,
                            HttpSession session) {

        User user = new User(username, email, password, role);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", user.getEmail());
            session.setAttribute("role", user.getRole());
            return "redirect:/order";
        }
        userService.registerUser(user);
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

        orderService.generateOrder(order);
        return "redirect:/receipt";
    }

    // Receipt page
    @GetMapping("/receipt")
    public String showReceipt(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getOrdersByUsername(username);
        model.addAttribute("orders", orders);

        double totalAmount = orders.stream().mapToDouble(Order::getTotal_price).sum();
        model.addAttribute("totalAmount", totalAmount);

        return "receipt";
    }

    // Order history
    @GetMapping("/history")
    public String showOrderHistory(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getOrdersByUsername(username);
        model.addAttribute("orders", orders);
        return "history";
    }

    // Admin page (Admin only)
    @GetMapping("/admin")
    public String showAdminDashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        double totalSales = orders.stream().mapToDouble(Order::getTotal_price).sum();
        model.addAttribute("totalSales", totalSales);

        return "admin";
    }
}

