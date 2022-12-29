package com.wokivovich.tm.controller;

import com.wokivovich.tm.entity.User;
import com.wokivovich.tm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration.html";
    }

    @PostMapping("/new-user")
    public String createNewUser (@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/login";
    }
}
