package com.example.ITdeviceMarket.controller;

import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        // Show the registration form
        @GetMapping("/register")
        public String showRegistrationForm(Model model) {
            model.addAttribute("user", new User());
            return "register"; // This will load register.html
        }

        // Process the registration form
        @PostMapping("/register")
        public String registerUser(@ModelAttribute("user") User user, Model model) {
            if (userService.findByEmail(user.getEmail()) != null) {
                model.addAttribute("error", "Email already exists! Try a different one.");
                return "register";
            }
            user.setRole("USER"); // Default role
            userService.registerUser(user);
            return "redirect:/login?success"; // Redirect to login page after registration
        }

        // Show login page
        @GetMapping("/login")
        public String showLoginPage() {
            return "login"; // This will load login.html
        }
    }


