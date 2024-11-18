package com.wor.dash.comment.model.controller;

import java.util.List;

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

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.comment.model.Comment;
import com.wor.dash.comment.model.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/wor/comment")
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody Comment comment) {
		commentService.addComment(comment);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping()
	public ResponseEntity<List<Comment>> getCommentList() {
        List<Comment> commentList = commentService.getAllComment();
        return ResponseEntity.ok(commentList);
    }
	
	
	
	@PutMapping
    public ResponseEntity<Void> updateChallenge(@RequestBody Comment comment) {
		commentService.editComment(comment);
        return ResponseEntity.ok().build();
    }
	
	@DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable int commentId) {
        commentService.removeComment(commentId);
        return ResponseEntity.ok().build();
    }
	
}
