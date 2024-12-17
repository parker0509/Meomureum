package com.example.demo.controller;


import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Tag(name = " Room API ", description = " 룸 관련 API ")
@RequestMapping("/room")
class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }



    @GetMapping
    @Operation(summary = "홈 페이지에서 방 목록 조회", description = " 홈 페이지 방 목록 조회")
    public String showRoomList(Model model) {

        List<Room> newroom = roomService.getAllRooms();
        model.addAttribute("newroom", newroom);

        return "room-list";

    }



    @GetMapping("{id}")
    public String getRoomDetails(@PathVariable(name="id")Long id,Model model){

        Room room = roomService.getByRoomID(id);

        model.addAttribute("room",room);

        return "room-detail";


    }

    @GetMapping("/new")
    @Operation(summary = " 방 생성 ", description = " 방 생성 ")
    public String showRoomForm(Model model) {

        model.addAttribute("rooms", new Room());
        return "room-form";
    }

    @PostMapping("/new")
    @Operation(summary = " 방 생성 " ,description = " 방 생성 DB전송 ")
    @ResponseBody
    public String saveRoom(@ModelAttribute Room room) {

        System.out.println("Room Name: " + room.getRoomName());
        System.out.println("Room Image URI: " + room.getRoomImageUri());
        roomService.saveRoom(room);
        return "방 등록이 완료 되었습니다.";
    }



}
