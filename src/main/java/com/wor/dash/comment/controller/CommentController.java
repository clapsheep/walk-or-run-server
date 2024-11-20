package com.wor.dash.comment.controller;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.comment.model.service.CommentService;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenge/{challengeId}/comment")
@RequiredArgsConstructor
@Tag(name = "Comment Controller", description = "챌린지 댓글 기능을 관리합니다.")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 추가", description = "특정 챌린지에 댓글을 달기 위한 API  \n\n <필수입력> \n\n ### path \n - challengeId : 댓글을 달 챌린지ID \n \n ### body \n - commentContent : 댓글 내용 \n - commentAuthorId : 로그인한 유저ID")
    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable int challengeId, @RequestBody Comment comment) {
        log.info("CommentController/createComment");
        try {
            commentService.addComment(challengeId, comment);
            return new ResponseEntity<>(new ApiResponse("success", "createComment", 200), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse("fail", "createComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "댓글 목록 조회(paging)", description = "특정 챌린지에 달린 댓글 목록을 가져오기 위한 API \n (페이지 정보 포함)  \n\n <필수입력> \n\n ### path \n - challengeId : 댓글을 단 챌린지ID \n\n ### query \n - page : 요청할 페이지 \n - size : 한 페이지 당 표시할 컨텐츠 수")
    @GetMapping
    public ResponseEntity<?> getCommentList(@PathVariable int challengeId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("CommentController/getCommentList");
        try {
            PageResponse<Comment> commentList = commentService.getCommentList(challengeId, page, size);
            if (commentList.getContent().isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("empty", "getCommentList", 204), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(commentList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse("fail", "getCommentList", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "댓글 수 조회", description = "특정 챌린지에 달린 댓글 수를 가져오기 위한 API  \n\n <필수입력> \n\n ### path \n - challengeId : 댓글을 단 챌린지ID")
    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getCountComments(@PathVariable int challengeId) {
        log.info("CommentController/getCountComments");
        try {
            int res = commentService.countComment(challengeId);
            return new ResponseEntity<>(new ApiResponse(res + "", "getCountComments", 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse("fail", "getCountComments", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "댓글 수정", description = "이미 작성한 댓글을 달기 위한 API  \n\n <필수입력> \n\n ### path \n - challengeId : 댓글을 단 챌린지ID \n - commentId : 댓글의 고유 ID \n \n ### body \n - commentContent : 수정할 댓글 내용")
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable("challengeId") int challengeId, @PathVariable("commentId") int commentId, @RequestBody Comment comment) {
        log.info("CommentController/updateComment");
        try {
            commentService.editComment(challengeId, commentId, comment);
            return new ResponseEntity<>(new ApiResponse("success", "updateComment", 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse("fail", "updateComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "댓글 삭제", description = "댓글을 삭제하기 위한 API  \n\n <필수입력> \n\n ### path \n - challengeId : 댓글을 단 챌린지ID \n - commentId : 댓글의 고유 ID")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("challengeId") int challengeId, @PathVariable("commentId") int commentId) {
        log.info("CommentController/deleteComment");
        try {
            commentService.removeComment(challengeId, commentId);
            return new ResponseEntity<>(new ApiResponse("success", "deleteComment", 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse("fail", "deleteComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
