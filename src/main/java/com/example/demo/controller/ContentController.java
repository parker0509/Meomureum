package com.example.demo.controller;

import com.example.demo.entity.Contents;
import com.example.demo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/contents")
public class ContentController {


    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public String getByContents(Model model){

        List<Contents> contents = contentService.getAllContents();
        if (contents == null) {
            contents = new ArrayList<>();  // 빈 리스트로 처리
        }
        model.addAttribute("content", contents);
        return "content-list";
    }

    @GetMapping("/new")
    public String getSaveByContents(Model model){
        model.addAttribute("content", new Contents()); // 변수 이름을 통일
        return "content-form";
    }

    @PostMapping("/new")
    public String SetSaveByContents(@ModelAttribute Contents contents){


        contentService.saveContents(contents);

        System.out.println("contents = " + contents);

        return "redirect:/api/contents"; // 리다이렉션 경로 수정


    }









}




