package com.example.demo.controller;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Contents;
import com.example.demo.service.CommentService;
import com.example.demo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final ContentService contentService;

    public CommentController(CommentService commentService, ContentService contentService) {
        this.commentService = commentService;
        this.contentService = contentService;
    }

    @PostMapping
    public String postComments(@RequestParam(name = "contentId") Long contentId,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "description") String description,
                               Model model) {

        Contents contents = contentService.findById(contentId).orElseThrow(() ->

                new IllegalArgumentException("게시물을 찾을 수 없습니다. Content의 확인해주세요."));

        Optional<Contents> contentss = contentService.findById(contentId);

        Comment comment = new Comment(name, description, contents);
        commentService.saveComment(comment);


        Contents updatedContents = contentService.findById(contentId).orElseThrow(() ->
                new IllegalArgumentException("게시물을 찾을 수 없습니다. Content의 확인해주세요."));

        model.addAttribute("comment", comment);
        model.addAttribute("content", updatedContents);  // 이 줄을 추가해야 합니다.
        return "content-detail";
    }
}
