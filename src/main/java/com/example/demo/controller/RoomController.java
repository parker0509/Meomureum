package com.example.demo.controller;

import com.example.demo.dto.RoomRequest;
import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Controller
@Tag(name = "Room API", description = "룸 관련 API로 방 목록 및 방 세부 사항을 관리합니다.")
@RequestMapping("/api/room")
class RoomController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final HttpSession httpSession;

    @Autowired
    public RoomController(RoomService roomService, RoomRepository roomRepository, HttpSession httpSession) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.httpSession = httpSession;
    }

    @Operation
    @GetMapping
    // 이 어노테이션은 JSON 형태로 데이터를 반환하도록 지정
    public String getHome(Model model) {
        // 검색어가 있다면 해당하는 방들만 반환
        // 검색어가 없으면 모든 방을 반환
        List<Room> AllRooms = roomService.getAllRooms();

        model.addAttribute("rooms",AllRooms);
        return "room-list";
    }

    @Operation
    @ResponseBody
    @GetMapping("/list")
        public List<Room> getHome() {
            return roomService.getAllRooms();
        }


    @Operation(summary = "방 세부 정보 조회", description = "특정 방의 세부 정보를 조회합니다.")
    @GetMapping("{id}")
    public String getRoomDetails(@PathVariable(name = "id") Long id, Model model) {
        Room room = roomService.getByRoomID(id);
        roomService.viewRooms(id);
        model.addAttribute("room", room);
        return "room-detail";
    }

    @Operation(summary = "방 검색", description = "방 이름, 주소, 가격 범위에 따라 방을 검색합니다.")
    @GetMapping("/search")
    public String searchRooms(
            @RequestParam(name = "roomName", required = false) String roomName,
            @RequestParam(name = "addressName", required = false) String addressName,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            Model model) {

        System.out.println("roomName: " + roomName);
        System.out.println("addressName: " + addressName);
        System.out.println("MinPrice: " + minPrice);
        System.out.println("MaxPrice: " + maxPrice);

        List<Room> rooms = roomService.getHomeSearchRooms(roomName, addressName, minPrice, maxPrice);

        System.out.println("rooms = " + rooms);

        model.addAttribute("rooms", rooms);

        return "room-list";

    }

    @Operation(summary = "방 생성 폼", description = "새로운 방을 생성하기 위한 폼을 보여줍니다.")
    @GetMapping("/new")
    public String showRoomForm(Model model) {
        model.addAttribute("rooms", new Room());
        return "room-form";
    }

    @Operation(summary = "새로운 방 생성", description = "새로운 방을 생성하고 그 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "방이 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 방 데이터가 제공되었습니다."),
            @ApiResponse(responseCode = "500", description = "방 생성 중 서버 오류가 발생했습니다.")
    })
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
        Room room = roomService.createRoomswithAddress(
                roomName, area, rentalPrice, addressQuery, description, roomImageUri, roomUse,
                petAllowed, smokingAllowed, shortTerm, parkingAvailable, femaleOnly, noMaintenanceFee, mealProvided, roomType
        );
        return ResponseEntity.ok(room);
    }

    @Operation(summary = "방 유형별 필터링", description = "방 유형에 따라 방을 필터링하여 조회합니다.")
    @GetMapping("/filter")
    public ResponseEntity<?> getFilterRoomsType(@RequestParam(name = "roomTypes", required = false) List<String> roomTypes, Model model) {
        try {
            List<Room> rooms = roomService.getRoomsByRoomTypes(roomTypes);
            return ResponseEntity.ok(rooms);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "방 지도 보기", description = "방들의 위치를 지도에서 확인할 수 있습니다.")
    @GetMapping("/map")
    public String getkakaoMap() {
        return "map";
    }

    @Operation(summary = "원룸 보기", description = "원룸 목록을 조회합니다.")
    @GetMapping("/oneroom")
    public String getRemainingRooms(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("single"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 원룸이 없습니다.");
        }

        model.addAttribute("rooms", rooms);
        return "one-room";
    }

    @Operation(summary = "게스트룸 보기", description = "게스트룸 목록을 조회합니다.")
    @GetMapping("/guest")
    public String getguestRoom() {
        return "guest-room";
    }

    @Operation(summary = "애완동물 허용 방 보기", description = "애완동물이 허용된 방 목록을 조회합니다.")
    @GetMapping("/pet")
    public String getWithPetRoom(Model model) {
        List<Room> petroom = roomService.getRoomsByPetAllowedTure();
        model.addAttribute("rooms", petroom);
        return "pet-room";
    }

    @Operation(summary = "공유 방 보기", description = "공유 방 목록을 조회합니다.")
    @GetMapping("/share")
    public String getShareRoom(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("shared"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 공유 방이 없습니다.");
        }

        model.addAttribute("rooms", rooms);
        return "share-room";
    }

    @Operation(summary = "코리빙 방 보기", description = "코리빙 방 목록을 조회합니다.")
    @GetMapping("/coliving")
    public String getColiving(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("coliving"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 코리빙 방이 없습니다.");
        }

        model.addAttribute("rooms", rooms);
        return "coliving-room";
    }

    @Operation(summary = "오피스텔 방 보기", description = "오피스텔 방 목록을 조회합니다.")
    @GetMapping("/officetel")
    public String getYouth(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("officetel"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 오피스텔 방이 없습니다.");
        }

        model.addAttribute("rooms", rooms);
        return "officetel-room";
    }

    @Operation(summary = "아파트 방 보기", description = "아파트 방 목록을 조회합니다.")
    @GetMapping("/apt")
    public String getMate(Model model) {
        List<Room> rooms = roomService.getRoomsByRoomTypes(Collections.singletonList("apart"));

        if (rooms.isEmpty()) {
            model.addAttribute("message", "등록된 아파트 방이 없습니다.");
        }

        model.addAttribute("rooms", rooms);
        return "apt-room";
    }

    @Operation(summary = "할인 방 보기", description = "할인된 방 목록을 조회합니다.")
    @GetMapping("/discount")
    public String getDiscount() {
        return "discount-room";
    }

    @Operation(summary = "비디오 방 보기", description = "비디오 방 목록을 조회합니다.")
    @GetMapping("/video")
    public String getVideo() {
        return "video-room";
    }
}
