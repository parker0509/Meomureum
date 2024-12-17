package com.example.demo.service;


import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);  // DB에 저장
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
               orElseThrow(()->new EntityNotFoundException("Room not Found id : " + id ));
    }

    //Update
    public void updateRooms(Long id, String roomDetails) {

        Optional<Room> roomOpt = roomRepository.findById(id);

        if(roomOpt.isPresent()){

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


    public List<Room> getLatestRooms(){
        return roomRepository.findTop4ByOrderByIdDesc();
    }

    public List<Room> getRemainingRooms(){
        return roomRepository.findAllByOrderByIdDesc();
    }



}
