package com.wor.dash.comment.model.controller;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.comment.model.service.CommentService;
import com.wor.dash.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 게시", description = "특정 챌린지에 댓글을 달기 위한 API  \n\n <필수입력> \n - challengeId : 댓글을 달 챌린지ID \n - commentContent : 댓글 내용 \n - commentAuthorId : 로그인한 유저ID")
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            commentService.addComment(comment);
            return new ResponseEntity<>(new ApiResponse("success", "createComment", 200), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "createComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "댓글 목록 가져오기(paging)", description = "특정 챌린지에 달린 댓글 목록을 가져오기 위한 API \n (페이지 정보 포함)  \n\n <필수입력> \n - challengeId : 댓글을 단 챌린지ID \n - commentContent : 수정할 댓글 내용 \n - commentAuthorId : 로그인한 유저ID")
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentList() {
        List<Comment> commentList = commentService.getAllComment();
        return ResponseEntity.ok(commentList);
    }

    @Operation(summary = "댓글 수정", description = "이미 작성한 댓글을 달기 위한 API  \n\n <필수입력> \n - challengeId : 댓글을 단 챌린지ID \n - commentContent : 수정할 댓글 내용 \n - commentAuthorId : 로그인한 유저ID")
    @PutMapping
    public ResponseEntity<ApiResponse> updateChallenge(@RequestBody Comment comment) {
        try {
            commentService.editComment(comment);
            return new ResponseEntity<>(new ApiResponse("success", "updateComment", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "updateComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제하기 위한 API  \n\n <필수입력> \n - commentId : 댓글의 고유 ID")
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
