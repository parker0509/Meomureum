package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/login")
public class LoginController {


    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    /*
    < details >
    TODO : 실제 로그인 기능 구현
            : 로그인 성공 후 로직 구현
             : 로그인 실패 후 로직 구현
    */

}
