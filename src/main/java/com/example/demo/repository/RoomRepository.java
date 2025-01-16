package com.example.demo.repository;


import com.example.demo.entity.Room;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    List<Room> findByRoomTypeIn(List<String> roomTypes);

    List<Room> findByPetAllowedTrue();


    /*
         • 추가 기능 01 - 15

         1 . 게시판 NEW / HOT 기능 추가

         @QURRY

         SELECT r FROM Room r WHERE r.created > CURRENT_DATE -7
         SELECT r FROM Room r ORDER BY r.viewCount DESC

     */
    // 7일 전 날짜를 기준으로 HOT 방들을 찾는 메서드
    // 7일 전 날짜 이후로 생성된 방들 중 조회수(viewCount)가 높은 순으로 HOT 방을 찾는 메서드
    @Query("SELECT r FROM Room r ORDER BY r.viewCount DESC")
    List<Room> findHotRoom(Pageable pageable);


    @Query("SELECT r FROM Room r ORDER BY r.created DESC")
    List<Room> findNewRoom(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo, Pageable pageable);


}
