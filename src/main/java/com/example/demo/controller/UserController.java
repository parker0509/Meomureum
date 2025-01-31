package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/join")
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
    @Operation(summary = "회원가입 페이지", description = "회원가입 폼을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 폼을 성공적으로 반환")
    })
    public String joinForm(Model model){
        model.addAttribute("user", new User());
        return "join-form";
    }

    @PostMapping
    @Operation(summary = "회원가입", description = "회원가입을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 성공적으로 완료됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public String joinUser(@ModelAttribute User user){
        userService.createUser(user);
        System.out.println(user + " 님의 회원 가입이 완료 되었습니다.");
        return "redirect:/api/login";
    }
}
