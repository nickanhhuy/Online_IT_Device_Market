//package com.example.ITdeviceMarket.controller;
//
//import com.example.ITdeviceMarket.model.Order;
//import com.example.ITdeviceMarket.model.User;
//import com.example.ITdeviceMarket.service.OrderService;
//import com.example.ITdeviceMarket.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//public class OrderController {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private UserService userService;
//
//    // Show the order page
//    @GetMapping("/order")
//    public String showOrderPage(Model model) {
//        model.addAttribute("order", new Order()); // Empty order object for form binding
//        return "order"; // Loads order.html
//    }
//
//    // Process order submission and show receipt directly
//    @PostMapping("/order")
//    public String processOrder(@ModelAttribute Order order, Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            System.out.println("User is not authenticated");
//            return "redirect:/login"; // Redirect if user is not authenticated
//        }
//
//        String email = authentication.getName();
//        User user = userService.findByEmail(email);
//
//        if (user == null) {
//            System.out.println("User not found in database!");
//            return "redirect:/login"; // Redirect if user is not found
//        }
//
//        System.out.println("User ID: " + user.getId());
//
//        order.setUser(user);
//        orderService.generateOrder(order);
//
//        model.addAttribute("order", order); // Pass the order object to the receipt page
//        return "receipt"; // Load receipt.html directly instead of redirecting
//    }
//
//    // Show receipt page (no longer needed since we pass order directly)
//    @GetMapping("/receipt")
//    public String showReceiptPage() {
//        return "redirect:/order"; // Prevent direct access to /receipt without an order
//    }
//
//    // Show order history
//    @GetMapping("/history")
//    public String showOrderHistory(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return "redirect:/login"; // Redirect if user is not authenticated
//        }
//
//        String email = authentication.getName();
//        User user = userService.findByEmail(email);
//
//        if (user == null) {
//            return "redirect:/login"; // Redirect if user not found
//        }
//
//        List<Order> orders = orderService.getOrdersByUser(user);
//        model.addAttribute("orders", orders);
//
//        return "history"; // Load order-history.html
//    }
//}





