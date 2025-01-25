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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller

@Tag(name = "Home", description = "연방 홈 페이지 관련 API")
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

    @GetMapping("/")
    @Operation(summary = "방 목록 조회", description = "방 목록 모든 조회 페이지")
    public String getHomeRooms(Model model, @AuthenticationPrincipal OAuth2User oAuth2User,
                               @AuthenticationPrincipal User user, Pageable pageable) {

        // OAuth2 사용자 로그인 처리
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
            System.out.println("UserName1: " + userName);

            // 세션에 사용자 이름 저장
            httpSession.setAttribute("username", userName);

            // 모델에 userName 추가
            model.addAttribute("username", userName);

        }
        // 일반 사용자 로그인 처리
        else if (user != null) {
            // 일반 사용자에서 사용자 이름 추출
            String userName = user.getUsername();

            // 디버깅: 사용자 정보 출력
            System.out.println("User: " + user);
            System.out.println("UserName2: " + userName);

            // 세션에 사용자 이름 저장
            httpSession.setAttribute("username", userName);

            // 모델에 userName 추가
            model.addAttribute("username", userName);
        }


        // 모든 방 목록
        List<Room> room = roomService.getAllRooms();
        model.addAttribute("rooms", room);

/*

               01-16

               NEW/HOT 기능 추가

               주석 처리


   // 최신 방을 가장 위에 띄우고, 그 외의 방들을 내림차순으로 가져오기
        List<Room> newrooms = roomService.getLatestRooms();
        // 최신 3개 방만 가져오기
        if (newrooms.size() > 3) {
            newrooms = newrooms.subList(0, 3); // 첫 3개 방만 가져오기
        }
        model.addAttribute("newrooms", newrooms);

        // 최신 4개 방을 제외한 나머지 방들
        List<Room> otherRooms = roomService.getRemainingRooms(); // getRemainingRooms 메소드에서 나머지 방들을 리턴
        model.addAttribute("otherRooms", otherRooms);*/


        /*

      기능 추가 NEW , HOT
      NEW 기능 - 최신 룸 목록
       hot rooms를 가져오는 API

        */

        List<Room> hotRoom = roomService.findHotRooms(pageable);
        System.out.println("Hot Rooms: " + hotRoom);  // 데이터 출력

        if(hotRoom.size()>3){
            hotRoom = hotRoom.subList(0,3);
        }
        model.addAttribute("hotRooms", hotRoom);

        List<Room> newRoom = roomService.findNewRooms(pageable);

        if (newRoom.size() > 3) {
            newRoom = newRoom.subList(0,3);
        }
        System.out.println("New Rooms: " + newRoom);  // 데이터 출력
        model.addAttribute("newRooms", newRoom);

        return "home";
    }








}