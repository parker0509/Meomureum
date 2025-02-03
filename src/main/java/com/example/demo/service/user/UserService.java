package com.example.demo.service.user;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // PasswordEncoder로 수정

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        Optional<User> existUser = userRepository.findByEmail(user.getEmail());

        if (existUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다. 확인 부탁 드립니다.");
        }
        // 기본 역할을 ROLE_USER로 설정
        user.setRole(Role.ROLE_USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> readUser(User user) {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        Optional<User> update = userRepository.findById(user.getId());

        if (update.isPresent()) {
            User existingUser = update.get();
            existingUser.setName(user.getName());
            userRepository.save(existingUser);
        }
    }

    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            userRepository.delete(user);
        } else {
            throw new RuntimeException("유저를 찾기 못했습니다.");
        }
    }

    public User findByUsername(String username){

        return userRepository.findByName(username);

    }

}
