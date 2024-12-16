package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
    @Operation(summary = " 방 목록 조회 ", description = " 방 목록 모든 조회 페이지")
    public String getHomeRooms(Model model) {


        // 모든 방 목록
        List<Room> room = roomService.getAllRooms();
        model.addAttribute("rooms", room);

        // 최신 방을 가장 위에 띄우고, 그 외의 방들을 내림차순으로 가져오기
        List<Room> rooms = roomService.getLatestAndOtherRooms();
        model.addAttribute("rooms", rooms);


        return "home";
    }


}
