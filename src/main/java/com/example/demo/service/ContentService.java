package com.example.demo.service;

import com.example.demo.entity.Contents;
import com.example.demo.entity.User;
import com.example.demo.repository.ContentsRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    private final ContentsRepository contentsRepository;
    private final UserRepository userRepository;

    @Autowired
    public ContentService(ContentsRepository contentsRepository, UserRepository userRepository) {
        this.contentsRepository = contentsRepository;
        this.userRepository = userRepository;
    }


    // 조회
    public List<Contents> getAllContents() {
        return contentsRepository.findAll();
    }


    //
    public Optional<Contents> findById(Long id){

        return contentsRepository.findById(id);
    }

    // 저장
    public void saveContents(Contents contents){

        contentsRepository.save(contents);
    }
}
