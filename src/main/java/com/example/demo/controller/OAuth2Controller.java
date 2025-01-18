package com.example.demo.controller;


import com.example.demo.service.user.CustomOAuth2UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Tag(name="OAuth",description = "OAuth 로그인 ")
@Controller
public class OAuth2Controller {

    private final HttpSession httpSession;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public OAuth2Controller(HttpSession httpSession, CustomOAuth2UserService customOAuth2UserService) {
        this.httpSession = httpSession;
        this.customOAuth2UserService = customOAuth2UserService;
    }


    @GetMapping("/testOAuth2")
    public String login(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        if (oAuth2User != null) {
            // OAuth2User에서 사용자 이름 추출
            String userName = (String) oAuth2User.getAttributes().get("name");
            System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());  // 사용자 정보 출력

            httpSession.setAttribute("userName", userName);  // 세션에 사용자 이름 저장
            model.addAttribute("userName", userName);  // 모델에 userName 추가
        }
        return "testOAuth2";  // "HomePage.html" 템플릿을 반환
    }

    @Operation(summary = "OAuth2 로그인", description = "OAuth2 로그인 후 사용자 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 로그인됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/oauth2/login")
    @ResponseBody
    public OAuth2User oauth2Login(@RequestParam("registrationId") String registrationId,
                                  @RequestParam("userNameAttributeName") String userNameAttributeName,
                                  OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 실제로 loadUser 메소드 호출
        return customOAuth2UserService.loadUser(userRequest);
    }


    @GetMapping("/HomePage")
    public String HomePage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        if (oAuth2User != null) {
            // OAuth2User에서 사용자 이름 추출
            String userName = (String) oAuth2User.getAttributes().get("name");

            // name이 없다면 properties에서 nickname을 가져오는 로직 추가
            if (userName == null) {
                Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
                if (properties != null) {
                    userName = (String) properties.get("nickname");
                }
            }

            // 디버깅: 사용자 정보 출력
            System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());
            System.out.println("UserName: " + userName);

            // 세션에 사용자 이름 저장
            httpSession.setAttribute("userName", userName);
            // 모델에 userName 추가
            model.addAttribute("userName", userName);
        }

        return "HomePage";  // HomePage.html 템플릿 반환
    }

}

