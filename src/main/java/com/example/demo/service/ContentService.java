package com.example.demo.service;

import com.example.demo.entity.Contents;
import com.example.demo.repository.ContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    private final ContentsRepository contentsRepository;

    @Autowired
    public ContentService(ContentsRepository contentsRepository) {
        this.contentsRepository = contentsRepository;
    }

    // 조회
    public List<Contents> getAllContents(){
        return contentsRepository.findAll();
    }

    // 저장
    public void saveContents(Contents contents){
        contentsRepository.save(contents);
    }
}
