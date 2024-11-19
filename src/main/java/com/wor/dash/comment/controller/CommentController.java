package com.wor.dash.comment.controller;

import java.util.List;

import com.wor.dash.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.comment.model.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            commentService.addComment(comment);
            return new ResponseEntity<>(new ApiResponse("success", "createComment", 200), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "createComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    // 구현 필요성? -> 댓글은 Challenge 가져올 때 Join으로 가져오는 것이 낫지 않은가?
    @GetMapping("")
    public ResponseEntity<List<Comment>> getCommentList() {
        List<Comment> commentList = commentService.getAllComment();
        return ResponseEntity.ok(commentList);
    }


    @PutMapping("")
    public ResponseEntity<ApiResponse> updateChallenge(@RequestBody Comment comment) {
        try {
            commentService.editComment(comment);
            return new ResponseEntity<>(new ApiResponse("success", "updateComment", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "updateComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteChallenge(@PathVariable int commentId) {
        try {
            commentService.removeComment(commentId);
            return new ResponseEntity<>(new ApiResponse("success", "deleteComment", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "deleteComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
