package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Contents;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ContentsRepository contentsRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ContentsRepository contentsRepository) {
        this.commentRepository = commentRepository;
        this.contentsRepository = contentsRepository;
    }

    public Comment saveComment(Comment comment) {

        return commentRepository.save(comment);
    }



}
