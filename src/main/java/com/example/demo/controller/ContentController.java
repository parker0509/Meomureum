package com.example.demo.controller;

import com.example.demo.entity.Contents;
import com.example.demo.service.ContentService;
import com.example.demo.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/contents")
public class ContentController {


    private final ContentService contentService;
    private final HttpSession httpSession;
    private final UserService userService;


    @Autowired
    public ContentController(ContentService contentService, HttpSession httpSession, UserService userService) {
        this.contentService = contentService;
        this.httpSession = httpSession;
        this.userService = userService;
    }

    @GetMapping
    public String getByContents(@AuthenticationPrincipal OAuth2User oAuth2User,@AuthenticationPrincipal User user, Model model) {

        List<Contents> contents = contentService.getAllContents();
        if (contents == null) {
            contents = new ArrayList<>();  // 빈 리스트로 처리
        }
        model.addAttribute("content", contents);

        // OAUTH
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
            httpSession.setAttribute("username", userName);
            // 모델에 userName 추가
            model.addAttribute("username", userName);
        }
        else if (user != null) {
            // 일반 사용자에서 사용자 이름 추출
            String userName = user.getUsername();

            // 디버깅: 사용자 정보 출력
            System.out.println("User: " + user);
            System.out.println("UserName: " + userName);

            // 세션에 사용자 이름 저장
            httpSession.setAttribute("username", userName);

            // 모델에 userName 추가
            model.addAttribute("username", userName);
        }
        return "content-list";
    }

    @GetMapping("/new")
    public String getSaveByContents(Model model) {
        model.addAttribute("content", new Contents()); // 변수 이름을 통일
        return "content-form";
    }

    @PostMapping("/new")
    public String SetSaveByContents(@RequestBody Contents contents) {

        contentService.saveContents(contents);

        System.out.println("contents = " + contents);

        return "redirect:/api/contents"; // 리다이렉션 경로 수정


    }

    @GetMapping("/content-form")
    public String writePage() {
        return "content-form";
    }
}





