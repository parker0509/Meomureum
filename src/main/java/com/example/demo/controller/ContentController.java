package com.example.demo.controller;

import com.example.demo.entity.Contents;
import com.example.demo.service.ContentService;
import com.example.demo.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/api/contents")
@Tag(name = "ContentAPI", description = "콘텐츠 관련 API")
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

    @Operation(
            summary = "모든 콘텐츠 목록 조회",
            description = "모든 콘텐츠 목록을 가져오고, OAuth2 사용자 또는 일반 사용자의 정보를 세션에 저장한 후 콘텐츠 목록을 반환합니다.",
            responses = {
                    @ApiResponse(description = "콘텐츠 목록 조회 성공", responseCode = "200"),
                    @ApiResponse(description = "콘텐츠 목록 조회 실패", responseCode = "500")
            }
    )
    @GetMapping
    public String getByContents(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @AuthenticationPrincipal User user,
            Model model) {

        List<Contents> contents = contentService.getAllContents();
        if (contents == null) {
            contents = new ArrayList<>();  // 빈 리스트로 처리
        }
        model.addAttribute("content", contents);

        // OAUTH
        if (oAuth2User != null) {
            String userName = (String) oAuth2User.getAttributes().get("name");

            if (userName == null) {
                Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
                if (properties != null) {
                    userName = (String) properties.get("nickname");
                }
            }

            httpSession.setAttribute("username", userName);
            model.addAttribute("username", userName);
        } else if (user != null) {
            String userName = user.getUsername();

            httpSession.setAttribute("username", userName);
            model.addAttribute("username", userName);
        }
        return "content-list";
    }

    @Operation(
            summary = "콘텐츠 작성 페이지",
            description = "새로운 콘텐츠를 작성할 수 있는 폼 페이지를 반환합니다."
    )
    @GetMapping("/new")
    public String getSaveByContents(Model model) {
        model.addAttribute("content", new Contents());
        return "content-form";
    }

    @Operation(
            summary = "콘텐츠 저장",
            description = "새로운 콘텐츠를 저장하고 콘텐츠 목록 페이지로 리다이렉션합니다.",
            responses = {
                    @ApiResponse(description = "콘텐츠 저장 성공", responseCode = "201"),
                    @ApiResponse(description = "콘텐츠 저장 실패", responseCode = "500")
            }
    )
    @PostMapping("/new")
    public String SetSaveByContents(@ModelAttribute Contents contents, @AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser) {

        String username = securityUser.getUsername();

        com.example.demo.entity.User user = userService.findByUsername(username);
        System.out.println("user = " + user);

        contents.setUser(user);

        contentService.saveContents(contents);
        return "redirect:/api/contents";
    }

    @Operation(
            summary = "콘텐츠 작성 폼 페이지",
            description = "콘텐츠 작성을 위한 폼 페이지를 반환합니다."
    )
    @GetMapping("/content-form")
    public String writePage() {
        return "content-form";
    }

    @GetMapping("/content-detail/{id}")
    public String getContentDetail(@PathVariable(name = "id") Long id,
                                   Model model) {
        Optional<Contents> contents = contentService.findById(id);

        if (contents.isPresent()) {
            model.addAttribute("content", contents.get());
            return "content-detail"; // content-detail.html을 반환
        } else {
            return "error"; // 콘텐츠가 없으면 error.html 페이지로 리다이렉트
        }
    }

}
