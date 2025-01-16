package com.example.demo.service;


import com.example.demo.entity.Address;
import com.example.demo.entity.Room;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.RoomRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RestTemplate restTemplate;
    private final AddressRepository addressRepository;

    @Autowired
    private KakaoApiService kakaoApiService;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public RoomService(RoomRepository roomRepository, RestTemplate restTemplate, AddressRepository addressRepository) {
        this.roomRepository = roomRepository;
        this.restTemplate = restTemplate;
        this.addressRepository = addressRepository;
    }






    /*
        추가 내용: 아래 파라미터들이 Room 객체 생성에 추가되었습니다.
        - 애완견 동반 여부(petAllowed)
        - 금연 여부(smokingAllowed)
        - 단기 가능 여부(shortTerm)
        - 주차 가능 여부(parkingAvailable)
        - 여성 전용 여부(femaleOnly)
        - 관리비 없음 여부(noMaintenanceFee)
        - 식사 제공 여부(mealProvided)
        - 방 유형(roomType)
    */

    public Room createRoomswithAddress(String roomName,
                                       double area,
                                       double rentalPrice,
                                       String addressQuery,
                                       String description,
                                       String roomImageUri,
                                       String roomUse,
                                       boolean petAllowed,
                                       boolean smokingAllowed,
                                       boolean shortTerm,
                                       boolean parkingAvailable,
                                       boolean femaleOnly,
                                       boolean noMaintenanceFee,
                                       boolean mealProvided,

                                       String roomType
    ) {


        Address address = kakaoApiService.getCoordinatesFromAddress(addressQuery);


        System.out.println("address = " + address);

        Room room = new Room();
        room.setRoomName(roomName);
        room.setArea(area);
        room.setRentalPrice(rentalPrice);
        room.setAddress(address);
        room.setLocation(roomUse);
        room.setDescription(description);
        room.setRoomImageUri(roomImageUri);

// 추가 내용
        room.setPetAllowed(petAllowed); // 애완견 동반 여부
        room.setRoomType(roomType); // 방 유형
        room.setFemaleOnly(femaleOnly); // 여성 전용 여부
        room.setMealProvided(mealProvided); // 식사 제공 여부
        room.setSmokingAllowed(smokingAllowed); // 금연 여부
        room.setNoMaintenanceFee(noMaintenanceFee); // 관리비 없음 여부
        room.setShortTerm(shortTerm); // 단기 가능 여부
        room.setParkingAvailable(parkingAvailable); // 주차 가능 여부


        // Room을 DB에 저장합니다.
        return roomRepository.save(room);


    }


    /*
    Complete Create

    TODO : 1.UD


    */
    // 방 모두 조회
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Read ID
    public Room getByRoomID(Long id) {
        return roomRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Room not Found id : " + id));
    }

    //Update
    public void updateRooms(Long id, String roomDetails) {
        Optional<Room> roomOpt = roomRepository.findById(id);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.setRoomName(room.getRoomName());
            room.setAddress(room.getAddress());
            room.setRentalPrice(room.getRentalPrice());
            room.setRoomUse(room.getRoomUse());
            room.setDescription(room.getDescription());
            room.setLocation(room.getLocation());

        }
    }

    //Delete
    /*
     * login 기능을 만든 뒤에 admin 권한을 줘야 함.
     *
     * */


    public List<Room> getLatestAndOtherRooms() {
        // 최신 방 (ID가 가장 큰 방) 가져오기
        Room latestRoom = roomRepository.findTopByOrderByIdDesc();

        // 최신 방을 제외한 나머지 방들 내림차순 정렬로 가져오기
        List<Room> otherRooms = roomRepository.findAllByIdNot(latestRoom.getId(), Sort.by(Sort.Order.desc("id")));

        // 최신 방을 첫 번째로 추가하고, 나머지 방들을 뒤에 이어서 출력
        List<Room> rooms = new ArrayList<>();
        rooms.add(latestRoom);  // 최신 방을 가장 위에 넣기
        rooms.addAll(otherRooms);  // 나머지 방들을 뒤에 추가

        return rooms;
    }


    public List<Room> getLatestRooms() {
        return roomRepository.findTop4ByOrderByIdDesc();
    }


    public List<Room> getRemainingRooms() {
        return roomRepository.findAllByOrderByIdDesc();
    }


    public List<Room> searchRooms(String addressName, Double mixPrice, Double maxPrice) {
        if (addressName != null && !addressName.isEmpty()) {
            return roomRepository.findByAddress_AddressNameContainingIgnoreCase(addressName);
        } else if (mixPrice != null && maxPrice != null) {
            return roomRepository.findByRentalPriceBetween(mixPrice, maxPrice);
        }
        return roomRepository.findAll();

    }

    public List<Room> getRoomsByRoomTypes(List<String> roomType) {

        if (roomType == null || roomType.isEmpty()) {

            throw new IllegalArgumentException("roomTypes 값이 비어 있습니다. 필터 조건을 제공해주세요.");

        } else {
            return roomRepository.findByRoomTypeIn(roomType);
        }
    }


    // 반려 동물 허가 룸 필터 기능 추가 01 - 15 ⚡⚡
    public List<Room> getRoomsByPetAllowedTure() {
        return roomRepository.findByPetAllowedTrue();
    }


    // NEW, HOT 기능 추가 01 - 15 ⚡⚡
    public List<Room> getNewRooms() {
        return roomRepository.findNewRooms();
    }


    // NEW, HOT 기능 추가 01 - 15 ⚡⚡
    public List<Room> getHotRooms(int page, int size) {
        return roomRepository.findHotRooms(PageRequest.of(page, size));
    }


    // Room을 확인시 조회수 하나씩 상승
    public Room viewRooms(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not Found"));

        room.setViewCount(room.getViewCount() + 1);
        roomRepository.save(room);

        return room;

    }


}