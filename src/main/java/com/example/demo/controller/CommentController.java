package com.example.demo.controller;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Contents;
import com.example.demo.service.CommentService;
import com.example.demo.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Autowired
    public CommentController(CommentService commentService, ContentService contentService) {
        this.commentService = commentService;
        this.contentService = contentService;
    }

    @Operation(summary = "댓글 작성", description = "주어진 게시물에 댓글을 작성합니다.")
    @PostMapping
    public String postComments(
            @Parameter(description = "게시물 ID", required = true) @RequestParam(name = "contentId") Long contentId,
            @Parameter(description = "댓글 작성자 이름", required = true) @RequestParam(name = "name") String name,
            @Parameter(description = "댓글 내용", required = true) @RequestParam(name = "description") String description,
            Model model) {

        Contents contents = contentService.findById(contentId).orElseThrow(() ->
                new IllegalArgumentException("게시물을 찾을 수 없습니다. Content의 확인해주세요."));

        Comment comment = new Comment(name, description, contents);
        commentService.saveComment(comment);

        Contents updatedContents = contentService.findById(contentId).orElseThrow(() ->
                new IllegalArgumentException("게시물을 찾을 수 없습니다. Content의 확인해주세요."));

        model.addAttribute("comment", comment);
        model.addAttribute("content", updatedContents);
        return "content-detail";
    }
}
