package com.example.demo.controller;


import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller  // 변경: @Controller → @RestController (JSON 응답을 직접 반환)
@Tag(name = " Room API ", description = " 룸 관련 API ")
@RequestMapping("/api/room")
class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "홈 페이지에서 방 목록 조회", description = "홈 페이지 방 목록 조회")
    public List<Room> getRoomList() {
        return roomService.getAllRooms();  // List<Room>을 JSON 형태로 반환
    }


   /*  c@GetMapping
    @Operation(summary = "홈 페이지에서 방 목록 조회", description = " 홈 페이지 방 목록 조회")
    public String showRoomList(Model model) {

        List<Room> newroom = roomService.getAllRooms();
        model.addAttribute("newroom", newroom);

        return "room-list";

    }*/


    @GetMapping("{id}")
    public String getRoomDetails(@PathVariable(name = "id") Long id, Model model) {

        Room room = roomService.getByRoomID(id);

        model.addAttribute("room", room);

        return "room-detail";


    }

    @GetMapping("/search")
    public List<Room> searchRooms(@RequestParam(required = false) String addressName,
                                  @RequestParam(required = false) double minPrice,
                                  @RequestParam(required = false) double maxPrice) {

        return roomService.searchRooms(addressName, minPrice, maxPrice);
    }

    @GetMapping("/new")
    @Operation(summary = " 방 생성 ", description = " 방 생성 ")
    public String showRoomForm(Model model) {

        model.addAttribute("rooms", new Room());
        return "room-form";
    }


    // Room 생성 API
    @PostMapping("/new")
    public ResponseEntity<Room> createRoom(
            @RequestParam(name = "roomName") String roomName,
            @RequestParam(name = "area") double area,
            @RequestParam(name = "rentalPrice") double rentalPrice,
            @RequestParam(name = "addressQuery") String addressQuery,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "roomImageUri") String roomImageUri,
            @RequestParam(name = "roomUse") String roomUse
    ) {

        // RoomService를 호출하여 방과 주소를 생성합니다.
        Room room = roomService.createRoomswithAddress(roomName, area, rentalPrice, addressQuery, description, roomImageUri, roomUse);

        // 생성된 Room 객체를 클라이언트에 응답
        return ResponseEntity.ok(room);
    }

    @GetMapping("/map")
    public String getkakaoMap() {
        return "map";
    }


}