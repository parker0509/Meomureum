package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import com.example.demo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller

@Tag(name = "Home", description = "연방 홈 페이지 관련 API")
public class HomeController {

    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public HomeController(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }

    @GetMapping("/")
    @Operation(summary = "방 목록 조회", description = "방 목록 모든 조회 페이지")
    public String getHomeRooms(Model model, @AuthenticationPrincipal User user) {

        // 로그인한 사용자 정보가 있으면
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        // 모든 방 목록
        List<Room> room = roomService.getAllRooms();
        model.addAttribute("rooms", room);

        // 최신 방을 가장 위에 띄우고, 그 외의 방들을 내림차순으로 가져오기
        List<Room> newrooms = roomService.getLatestRooms();
        // 최신 3개 방만 가져오기
        if (newrooms.size() > 3) {
            newrooms = newrooms.subList(0, 3); // 첫 3개 방만 가져오기
        }
        model.addAttribute("newrooms", newrooms);

        // 최신 4개 방을 제외한 나머지 방들
        List<Room> otherRooms = roomService.getRemainingRooms(); // getRemainingRooms 메소드에서 나머지 방들을 리턴
        model.addAttribute("otherRooms", otherRooms);

        return "home";
    }



    @GetMapping("/api/guest")

    public String getguestRoom(){
        return "guest-room";
    }

    @GetMapping("/api/oneroom")

    public String getOneRoom(){
        return "one-room";
    }


    @GetMapping("/api/pet")

    public String getWithPetRoom(){
        return "pet-room";
    }

    @GetMapping("/api/share")

    public String getShareRoom(){
        return "share-room";
    }

    @GetMapping("/api/coliving")

    public String getColiving(){
        return "coliving-room";
    }

    @GetMapping("/api/youth")
    public String getYouth(){
        return "youth-room";
    }

    @GetMapping("/api/mate")

    public String getMate(){
        return "mate-room";
    }

    @GetMapping("/api/deal") @ResponseBody
    public String getDeal(){
        return "deal-room";
    }

    @GetMapping("/api/discount")

    public String getDiscount(){
        return "discount-room";
    }

    @GetMapping("/api/video")

    public String getVideo(){
        return "video-room";
    }






}