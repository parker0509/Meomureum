package com.example.demo.config;


import com.example.demo.service.user.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    // BCryptPasswordEncoder를 빈으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http

                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))

                .formLogin(form -> form
                        .loginPage("/api/login")  // 로그인 페이지 경로
                        .permitAll()           // 로그인 페이지는 누구나 접근 가능
                        .defaultSuccessUrl("/", true)  // 로그인 성공 후 리디렉션
                        .failureUrl("/api/login?error=true")  // 로그인 실패 시 리디렉션  // 로그인 실패 시 리디렉션할 URL
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/")  // 로그아웃 후 메인 페이지로 리디렉션
                        .invalidateHttpSession(true)  // 세션 무효화
                        .clearAuthentication(true)   // 인증 정보 삭제
                        .deleteCookies("JSESSIONID") // 세션 쿠키 삭제
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/"); // 로그인 후 홈 페이지로 리디렉션
                        })
                )

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/swagger-ui/**","/api/room/booking","/api/contents/content-form")
                        .authenticated()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().permitAll());


        return http.build();
    }


}


