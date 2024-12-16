package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
/*
    v.0.0.1
    CREATE , READ , UPDATE, DELETED

*/

    //save (저장)
    public User createUser(User user) {

        User existUser = userRepository.findByEmail(user.getEmail());

        if (existUser != null) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다. 확인 부탁 드립니다.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //Read ( 읽기 )
    public List<User> readUser(User user) {
        return userRepository.findAll();
    }

    //UPDATE
    public void updateUser(User user){

        Optional<User> update = userRepository.findById(user.getId());

        if(update.isPresent()){

            user.setId(user.getId());
            user.setName(user.getName());

        }

    }

    //DELETE (ALL)
    public void deleteUser(Long id) {

        Optional<User> userOpt =userRepository.findById(id);


        if (userOpt.isPresent()) {
            User user = userOpt.get();
            userRepository.delete(user);
        } else {

            throw new RuntimeException("유저를 찾기 못했습니다.");

        }

    }


}
