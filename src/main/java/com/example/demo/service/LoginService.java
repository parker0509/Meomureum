package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public Optional<User> LoginUserCheck(String email, String password, HttpSession httpSession) {


        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent() && user.get().getPassword() != null && bCryptPasswordEncoder.matches(password, user.get().getPassword())) {

            httpSession.setAttribute("user", user);

            System.out.println("유저 정보" + user);

            return user;


        } else {

            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못 됐습니다.");
        }


    }


}
