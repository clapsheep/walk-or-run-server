package com.wor.dash.follow.controller;

import com.wor.dash.follow.model.service.FollowService;
import com.wor.dash.response.ApiResponse;
import com.wor.dash.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/{userId}/follow")
@RequiredArgsConstructor
@Tag(name = "Follow Controller", description = "팔로우 기능을 관리합니다.")
@Slf4j
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로우", description = "팔로우를 위한 API  \n\n <필수입력>  \n\n ### path  \n - userId : 로그인한 유저ID \n - targetUserId : 팔로우할 유저ID")
    @PostMapping("/{targetUserId}")
    public ResponseEntity<ApiResponse> followUser(@PathVariable int userId, @PathVariable int targetUserId) {
        log.info("FollowController/followUser");
        try {
            followService.addFollow(userId, targetUserId);
            return new ResponseEntity<>(new ApiResponse("success", "follow", 200), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse("fail", "follow", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "언팔로우", description = "언팔로우를 위한 API  \n\n <필수입력>  \n\n ### path  \n - userId : 로그인한 유저ID \n - targetUserId : 언팔로우할 유저ID")
    @DeleteMapping("/{targetUserId}")
    public ResponseEntity<ApiResponse> unfollowUser(@PathVariable int userId, @PathVariable int targetUserId) {
        log.info("FollowController/unfollowUser");
        try {
            boolean res = followService.removeFollow(userId, targetUserId);
            if (!res) {
                return new ResponseEntity<>(new ApiResponse("fail", "unfollow", 400), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new ApiResponse("success", "unfollow", 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse("fail", "follow", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "팔로우 여부 확인", description = "팔로우 여부 확인을 위한 API  \n\n <필수입력>  \n\n ### path  \n - userId : 로그인한 유저ID \n - targetUserId : 타겟 유저ID")
    @GetMapping("/{targetUserId}/status")
    public ResponseEntity<?> checkFollow(@PathVariable int userId, @PathVariable int targetUserId) {
        log.info("FollowController/checkFollow");
        try {
            boolean isFollow = followService.checkFollow(userId, targetUserId);
            return new ResponseEntity<>(isFollow, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "팔로워 리스트", description = "로그인한 사용자를 팔로우 하고 있는 유저 리스트 API  \n\n <필수입력>  \n\n ### path  \n - userId : 로그인한 유저ID")
    @GetMapping("/followers")
    public ResponseEntity<?> getFollowers(@PathVariable int userId) {
        log.info("FollowController/getFollowers");
        try {
            List<User> followers = followService.getFollowerList(userId);
            if (followers.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("empty", "followers", 204), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(followers, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "팔로잉 리스트", description = "로그인한 사용자가 팔로우 하고 있는 유저 리스트 API  \n\n <필수입력>  \n\n ### path  \n - userId : 로그인한 유저ID")
    @GetMapping("/followings")
    public ResponseEntity<?> getFollowing(@PathVariable int userId) {
        log.info("FollowController/getFollowing");
        try {
            List<User> following = followService.getFollowingList(userId);
            if (following.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("empty", "followers", 204), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(following, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}