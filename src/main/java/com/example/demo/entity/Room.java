package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 방 ID

    private String roomName;
    private String roomImageUri;


    @Column(nullable = false)
    private double area;  // 전용 면적 (평수 또는 m²)

    @Column(nullable = false)
    private double rentalPrice;  // 임대료
    private String location;  // 위치
    private String description;
    private String roomUse;
    private int roomNumber; // 방수
    private int toilet; // 화장실 수
    private int living; // 거실 수
    private int kitchen; // 부엌 수

    private Boolean shortTerm;
    private Boolean parkingAvailable;
    private Boolean femaleOnly;
    private Boolean noMaintenanceFee;
    private Boolean mealProvided;
    private Boolean petAllowed;
    private Boolean SmokingAllowed;
    private String roomType;


    public Boolean getSmokingAllowed() {
        return SmokingAllowed;
    }

    public void setSmokingAllowed(Boolean smokingAllowed) {
        SmokingAllowed = smokingAllowed;
    }

    public Boolean getPetAllowed() {
        return petAllowed;
    }

    public void setPetAllowed(Boolean petAllowed) {
        petAllowed = petAllowed;
    }

    public Boolean getShortTerm() {
        return shortTerm;
    }

    public void setShortTerm(Boolean shortTerm) {
        this.shortTerm = shortTerm;
    }

    public Boolean getParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(Boolean parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    public Boolean getFemaleOnly() {
        return femaleOnly;
    }

    public void setFemaleOnly(Boolean femaleOnly) {
        this.femaleOnly = femaleOnly;
    }

    public Boolean getNoMaintenanceFee() {
        return noMaintenanceFee;
    }

    public void setNoMaintenanceFee(Boolean noMaintenanceFee) {
        this.noMaintenanceFee = noMaintenanceFee;
    }

    public Boolean getMealProvided() {
        return mealProvided;
    }

    public void setMealProvided(Boolean mealProvided) {
        this.mealProvided = mealProvided;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;// Address와의 관계 설정


    // 추가된 created 필드
    private LocalDateTime created;  // 방 생성 시간을 저장

/*
    // ⚡ 추가 된 기능
    // 01-15 추가 된 기능 NEW / HOT
*/

    private int viewCount;

    /*

        Getter , Setter 부분

     */




    public int getViewCount() {
        return viewCount;
    }

    // 01-15 추가 된 기능 NEW / HOT
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @PrePersist
    public void prePersist() {
        // 엔티티가 저장되기 전에 created 필드 자동으로 현재 시간으로 설정
        this.created = LocalDateTime.now();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // Getter, Setter, 기타 필요한 코드들
    public LocalDateTime getCreated() {
        return created;
    }


    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomImageUri() {
        return roomImageUri;
    }

    public void setRoomImageUri(String roomImageUri) {
        this.roomImageUri = roomImageUri;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomUse() {
        return roomUse;
    }

    public void setRoomUse(String roomUse) {
        this.roomUse = roomUse;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getToilet() {
        return toilet;
    }

    public void setToilet(int toilet) {
        this.toilet = toilet;
    }

    public int getLiving() {
        return living;
    }

    public void setLiving(int living) {
        this.living = living;
    }

    public int getKitchen() {
        return kitchen;
    }

    public void setKitchen(int kitchen) {
        this.kitchen = kitchen;
    }
}