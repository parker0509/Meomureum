package com.example.demo.controller;

import com.example.demo.dto.RoomRequest;
import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Controller  // 변경: @Controller → @RestController (JSON 응답을 직접 반환)
@Tag(name = " Room API ", description = " 룸 관련 API ")
@RequestMapping("/api/room")
class RoomController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final HttpSession httpSession;

    public RoomController(RoomService roomService, RoomRepository roomRepository, HttpSession httpSession) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.httpSession = httpSession;
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
        roomService.viewRooms(id);

        model.addAttribute("room", room);

        return "room-detail";


    }

    // 기능 추가 NEW , HOT
    // NEW 기능 - 최신 룸 목록
    // 'hot rooms'를 가져오는 API
/*    @GetMapping("/hotroom")
    public String getHotRooms(Pageable pageable,Model model) {
        List<Room> hotRoom = roomService.findHotRooms(pageable);
        System.out.println("Hot Rooms: " + hotRoom);  // 데이터 출력
        model.addAttribute("hotRooms", hotRoom);
        return "home";  // RoomService 메서드를 호출
    }

    // 예시: '새로운 방'을 가져오는 API
    @GetMapping("/newroom")
    public String getNewRooms(Pageable pageable,Model model) {
        List<Room> rooms = roomService.findNewRooms(pageable);
        model.addAttribute("rooms", rooms);  // "rooms"라는 이름으로 데이터를 전달
        return "home";  // home.html로 반환
    }*/


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
            @RequestParam(name = "roomUse") String roomUse,
            @RequestParam(name = "petAllowed", required = false) boolean petAllowed,
            @RequestParam(name = "smokingAllowed", required = false) boolean smokingAllowed,
            @RequestParam(name = "shortTerm", required = false) boolean shortTerm,
            @RequestParam(name = "parkingAvailable", required = false) boolean parkingAvailable,
            @RequestParam(name = "femaleOnly", required = false) boolean femaleOnly,
            @RequestParam(name = "noMaintenanceFee", required = false) boolean noMaintenanceFee,
            @RequestParam(name = "mealProvided", required = false) boolean mealProvided,
            @RequestParam(name = "roomType") String roomType
    ) {
        // RoomService를 호출하여 방과 주소를 생성합니다.
        Room room = roomService.createRoomswithAddress(
                roomName, area, rentalPrice, addressQuery, description, roomImageUri, roomUse,
                petAllowed, smokingAllowed, shortTerm, parkingAvailable, femaleOnly, noMaintenanceFee, mealProvided, roomType
        );
        // 생성된 Room 객체를 클라이언트에 응답
        return ResponseEntity.ok(room);
    }


    //  Filter 핕터 파트

    @GetMapping("/filter")
    public ResponseEntity<?> getFilterRoomsType(@RequestParam(name = "roomTypes", required = false) List<String> roomTypes, Model model) {

        try {
            List<Room> rooms = roomService.getRoomsByRoomTypes(roomTypes);
            return ResponseEntity.ok(rooms);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @GetMapping("/map")
    public String getkakaoMap() {
        return "map";
    }

    /*    @GetMapping("/oneroom")
        public String getOneRoom() {
            return "one-room";
        }*/
    @GetMapping("/oneroom")
    public String getRemainingRooms(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("single"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 원룸이 없습니다.");  // 방이 없을 때 메시지 추가
        }

        model.addAttribute("rooms", rooms);
        return "one-room";
    }

    @GetMapping("/guest")
    public String getguestRoom() {
        return "guest-room";
    }

    @GetMapping("/pet")
    public String getWithPetRoom(Model model) {
        List<Room> petroom = roomService.getRoomsByPetAllowedTure();
        model.addAttribute("rooms", petroom);
        return "pet-room";
    }

    @GetMapping("/share")
    public String getShareRoom(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("shared"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 원룸이 없습니다.");  // 방이 없을 때 메시지 추가
        }

        model.addAttribute("rooms", rooms);
        return "share-room";
    }

    @GetMapping("/coliving")
    public String getColiving(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("coliving"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 원룸이 없습니다.");  // 방이 없을 때 메시지 추가
        }

        model.addAttribute("rooms", rooms);
        return "coliving-room";
    }

    @GetMapping("/officetel")
    public String getYouth(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("officetel"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 원룸이 없습니다.");  // 방이 없을 때 메시지 추가
        }

        model.addAttribute("rooms", rooms);
        return "officetel-room";
    }

    @GetMapping("/apt")
    public String getMate(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("apart"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 원룸이 없습니다.");  // 방이 없을 때 메시지 추가
        }

        model.addAttribute("rooms", rooms);
        return "apt-room";
    }

    @GetMapping("/discount")
    public String getDiscount() {
        return "discount-room";
    }

    @GetMapping("/video")
    public String getVideo() {
        return "video-room";
    }








}