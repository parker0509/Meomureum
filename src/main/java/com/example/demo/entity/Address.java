package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressName;
    private String region1DepthName;
    private String region2DepthName;
    private String region3DepthName;
    private String region4DepthName;
    private String x; // 위도
    private String y; // 경도

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getRegion1DepthName() {
        return region1DepthName;
    }

    public void setRegion1DepthName(String region1DepthName) {
        this.region1DepthName = region1DepthName;
    }

    public String getRegion2DepthName() {
        return region2DepthName;
    }

    public void setRegion2DepthName(String region2DepthName) {
        this.region2DepthName = region2DepthName;
    }

    public String getRegion3DepthName() {
        return region3DepthName;
    }

    public void setRegion3DepthName(String region3DepthName) {
        this.region3DepthName = region3DepthName;
    }

    public String getRegion4DepthName() {
        return region4DepthName;
    }

    public void setRegion4DepthName(String region4DepthName) {
        this.region4DepthName = region4DepthName;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
