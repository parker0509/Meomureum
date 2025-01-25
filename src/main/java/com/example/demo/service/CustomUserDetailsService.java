package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("Loading user by email: " + email);
        // DB에서 사용자를 이메일로 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        System.out.println("User loaded: " + user.getEmail());

        // Spring Security User 객체로 반환
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getAuthorities() // 권한 정보 (예: ROLE_USER, ROLE_ADMIN 등)
        );
    }
}
