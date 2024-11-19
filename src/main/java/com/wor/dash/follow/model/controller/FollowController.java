package com.wor.dash.follow.model.controller;

import com.wor.dash.follow.model.Follow;
import com.wor.dash.follow.model.service.FollowService;
import com.wor.dash.response.ApiResponse;
import com.wor.dash.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로우 하기", description = "팔로우를 위한 API  \n\n <필수입력> \n - followerId : 로그인한 유저ID \n - followingId : 팔로우할 유저ID")
    @PostMapping
    public ResponseEntity<ApiResponse> followUser(@RequestBody Follow follow) {
        try {
            followService.addFollow(follow);
            return new ResponseEntity<>(new ApiResponse("success", "follow", 200), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "follow", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "언팔로우 하기", description = "언팔로우를 위한 API  \n\n <필수입력> \n - followerId : 로그인한 유저ID \n - followingId : 팔로우할 유저ID")
    @DeleteMapping
    public ResponseEntity<ApiResponse> unfollowUser(@RequestBody Follow follow) {
        try {
            boolean res = followService.removeFollow(follow);
            if (!res) {
                return new ResponseEntity<>(new ApiResponse("fail", "unfollow", 400), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new ApiResponse("success", "unfollow", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "follow", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "팔로우 여부 확인", description = "팔로우 여부 확인을 위한 API  \n\n <필수입력> \n - followerId : 로그인한 유저ID \n - followingId : 타겟 유저ID")
    @PostMapping("/check")
    public ResponseEntity<?> checkFollow(@RequestBody Follow follow) {
        try {
            boolean isFollow = followService.checkFollow(follow);
            return new ResponseEntity<>(isFollow, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "팔로워 리스트", description = "로그인한 사용자를 팔로우 하고 있는 유저 리스트 API  \n\n <필수입력> \n - userId : 로그인한 유저ID")
    @GetMapping("/followers/{userId}")
    public ResponseEntity<?> getFollowers(@PathVariable int userId) {
        try {
            List<User> followers = followService.getFollowerList(userId);
            return new ResponseEntity<>(followers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "팔로잉 리스트", description = "로그인한 사용자가 팔로우 하고 있는 유저 리스트 API  \n\n <필수입력> \n - userId : 로그인한 유저ID")
    @GetMapping("/following/{userId}")
    public ResponseEntity<?> getFollowing(@PathVariable int userId) {
        try {
            List<User> following = followService.getFollowingList(userId);
            return new ResponseEntity<>(following, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
