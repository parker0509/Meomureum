package com.example.demo.repository;


import com.example.demo.entity.Room;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // 최신 방을 가장 위로 가져오는 쿼리
    Room findTopByOrderByIdDesc();  // ID가 내림차순으로 최신 방 한 개를 가져옴

    // 특정 ID를 제외하고 방들을 내림차순으로 가져오는 쿼리// 특정 ID를 제외하고 정렬
    List<Room> findAllByIdNot(Long id, Sort id1);

    // 최신 방 4개만 가져오기
    List<Room> findTop4ByOrderByIdDesc();  // 최신 방 4개만 가져오는 메소드

    // 전체 방을 내림차순으로 정렬해서 가져오기
    List<Room> findAllByOrderByIdDesc();  // 모든 방을 내림차순으로 가져오는 메소드

    List<Room> findByAddress_AddressNameContainingIgnoreCase(String addressName);

    List<Room> findByRentalPriceBetween(double minPrice, double maxPrice);


}
