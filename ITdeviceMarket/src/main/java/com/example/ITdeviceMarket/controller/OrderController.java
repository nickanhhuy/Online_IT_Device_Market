package com.example.ITdeviceMarket.controller;

import com.example.ITdeviceMarket.model.Order;
import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.service.OrderService;
import com.example.ITdeviceMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // Show the order page
    @GetMapping("/order")
    public String showOrderPage(Model model) {
        model.addAttribute("order", new Order()); // Empty order object
        return "order"; // Loads order.html
    }

    // Process order submission and redirect to receipt
    @PostMapping("/order")
    public String processOrder(@ModelAttribute("order") Order order) {
        // Get logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return "redirect:/login"; // Redirect to login if user not found
        }

        // Assign user to order and save it
        order.setUser(user);
        orderService.generateOrder(order);

        // Redirect to receipt page with order ID
        return "redirect:/receipt?orderId=" + order.getId();
    }

    // Show receipt page
    @GetMapping("/receipt")
    public String showReceiptPage(@RequestParam("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderByUserId(orderId);

        if (order == null) {
            return "redirect:/order"; // Redirect to order page if no order found
        }

        model.addAttribute("order", order);
        return "receipt"; // Load receipt.html
    }

    // Show order history
    @GetMapping("/order-history")
    public String showOrderHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("orders", orderService.getOrdersByUser(user));
        return "order-history"; // Load order-history.html
    }
}




