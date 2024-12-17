package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User API", description = "유저 관련 API")
@RequestMapping("/join")
@Controller
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String joinForm(Model model){

        model.addAttribute("user",new User());

        return "join-form";
    }


    @PostMapping
    @Operation(summary = "회원가입")
    public String joinUser(@ModelAttribute User user){

        userService.createUser(user);

        return "redirect:/login";
    }


}


