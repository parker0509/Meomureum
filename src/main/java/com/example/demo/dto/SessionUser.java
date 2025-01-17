package com.example.demo.dto;

import com.example.demo.entity.User;

import java.io.Serializable;

public class SessionUser implements Serializable {

    private final String name;
    private final String gender;
    private final String email;
    private final String age;

    public SessionUser(User user) {
        this.name = user.getName();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.age = user.getAge();
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }
}
