package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/login")
@Tag(name = "Login API", description = "사용자 로그인 관련 API")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(
            summary = "로그인 페이지 조회",
            description = "사용자가 로그인 페이지로 이동할 수 있도록 로그인 페이지를 반환합니다."
    )
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
