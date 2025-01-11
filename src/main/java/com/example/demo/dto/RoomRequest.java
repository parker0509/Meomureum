package com.example.demo.dto;

import com.example.demo.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {
    private String roomName;
    private String roomImageUri;
    private Address address;
    private int roomNumber;
    private int toilet;
    private int kitchen;
    private int area;
    private int rentalPrice;
    private boolean shortTerm; // 단기 가능 여부
    private boolean parkingAvailable; // 주차 가능 여부
    private boolean femaleOnly; // 여성 전용 여부
    private boolean noMaintenanceFee; // 관리비 없음 여부
    private boolean mealProvided; // 식사 제공 여부
    private String roomType; // 방 유형 (single, double, shared 등)
    private String description;
}
