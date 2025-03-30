package com.example.ITdeviceMarket.controller;

import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.repository.OrderRepository;
import com.example.ITdeviceMarket.repository.UserRepository;
import com.example.ITdeviceMarket.service.OrderService;
import com.example.ITdeviceMarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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



    // Login page


    @PostMapping("/register")
    public String loginUser(@RequestParam String name,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String role,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = new User(name, email, password, role);


        session.setAttribute("username", user.getEmail());
        session.setAttribute("role", user.getRole());


        userService.registerUser(user);
        return "redirect:/order";
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

        return "order";
    }

    @PostMapping("/order")
    public String placeOrder(@RequestParam String device,
                             @RequestParam String deviceColor,
                             @RequestParam int deviceQuantity,
                             HttpServletRequest request,
                             Model model) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");


        double price = device.equalsIgnoreCase("android") ? 900 : 950;
        double totalAmount = price * deviceQuantity;
        Order order = new Order(username,device,deviceColor,deviceQuantity,totalAmount);
        orderService.generateOrder(order);
        model.addAttribute("order", order);
        return "receipt";
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

