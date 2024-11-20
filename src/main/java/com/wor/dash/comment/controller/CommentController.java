package com.wor.dash.comment.controller;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.comment.model.service.CommentService;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
@Tag(name = "Comment Controller", description = "챌린지 댓글 기능을 관리합니다.")
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

    @Operation(summary = "댓글 목록 가져오기(paging)", description = "특정 챌린지에 달린 댓글 목록을 가져오기 위한 API \n (페이지 정보 포함)  \n\n <필수입력> \n - challengeId : 댓글을 단 챌린지ID \n - page : 요청할 페이지 \n - size : 한 페이지 당 표시할 컨텐츠 수")
    @GetMapping("/{challengeId}")
    public ResponseEntity<?> getCommentList(@PathVariable int challengeId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            PageResponse<Comment> commentList = commentService.getCommentList(challengeId, page, size);
            if (commentList.getContent().isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("empty", "getCommentList", 204), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(commentList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "getCommentList", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "댓글 갯수 가져오기", description = "특정 챌린지에 달린 댓글 수를 가져오기 위한 API  \n\n <필수입력> \n - challengeId : 댓글을 단 챌린지ID")
    @GetMapping("/count/{challengeId}")
    public ResponseEntity<ApiResponse> getCountComments(@PathVariable int challengeId) {
        try {
            int res = commentService.countComment(challengeId);
            return new ResponseEntity<>(new ApiResponse(res + "", "getCountComments", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "getCountComments", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "댓글 수정", description = "이미 작성한 댓글을 달기 위한 API  \n\n <필수입력> \n - commentId : 댓글의 고유 ID \n - commentContent : 수정할 댓글 내용")
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
