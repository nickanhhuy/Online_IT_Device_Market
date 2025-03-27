package com.example.ITdeviceMarket.controller;

import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // This will load register.html
    }

    // Process the registration form
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Check if email already exists
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists! Try a different one.");
            return "register";  // Stay on the register page if email exists
        }

        // Set default role for the user
        user.setRole("USER");

        // Encode the password before saving to the database (add PasswordEncoder in your service)
        userService.registerUser(user);

        // Add success message
        model.addAttribute("success", "Registration successful! You can now log in.");

        // Redirect to the login page after successful registration
        return "redirect:/login"; // Redirect to login page after registration
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This will load login.html
    }
}




