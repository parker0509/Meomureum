package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import com.example.demo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@Tag(name = "Home", description = "머무름 홈 페이지 관련 API")
public class HomeController {

    private final UserService userService;
    private final RoomService roomService;
    private final HttpSession httpSession;

    @Autowired
    public HomeController(UserService userService, RoomService roomService, HttpSession httpSession) {
        this.userService = userService;
        this.roomService = roomService;
        this.httpSession = httpSession;
    }

    @Operation(
            summary = "방 목록 조회",
            description = "홈 페이지에 모든 방 목록을 조회하고, 최신 방과 인기 방을 가져옵니다. 사용자 로그인 상태에 따라 세션에 사용자 이름을 저장하고 모델에 전달합니다."
    )
    @GetMapping("/")
    public String getHomeRooms(Model model,
                               @AuthenticationPrincipal OAuth2User oAuth2User,
                               @AuthenticationPrincipal User user,
                               Pageable pageable) {

        // OAuth2 사용자 로그인 처리
        if (oAuth2User != null) {
            String userName = (String) oAuth2User.getAttributes().get("name");
            if (userName == null) {
                Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
                if (properties != null) {
                    userName = (String) properties.get("nickname");
                }
            }

            // 세션에 사용자 이름 저장
            httpSession.setAttribute("username", userName);
            model.addAttribute("username", userName);
        }
        // 일반 사용자 로그인 처리
        else if (user != null) {
            String userName = user.getUsername();
            httpSession.setAttribute("username", userName);
            model.addAttribute("username", userName);
        }

        // 모든 방 목록 조회
        List<Room> room = roomService.getAllRooms();
        model.addAttribute("rooms", room);

        // 최신 방 목록 (최대 3개)
        List<Room> hotRoom = roomService.findHotRooms(pageable);
        if (hotRoom.size() > 3) {
            hotRoom = hotRoom.subList(0, 3);
        }
        model.addAttribute("hotRooms", hotRoom);

        // 최신 방 목록 (최대 3개)
        List<Room> newRoom = roomService.findNewRooms(pageable);
        if (newRoom.size() > 3) {
            newRoom = newRoom.subList(0, 3);
        }
        model.addAttribute("newRooms", newRoom);

        return "home";
    }
}
