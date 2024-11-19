package com.wor.dash.follow.model.controller;

import java.util.List;

import com.wor.dash.response.ApiResponse;
import com.wor.dash.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wor.dash.follow.model.Follow;
import com.wor.dash.follow.model.service.FollowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/follow")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;

	@PostMapping
    public ResponseEntity<ApiResponse> followUser(@RequestBody Follow follow) {
        try{
            followService.addFollow(follow);
            return new ResponseEntity<>(new ApiResponse("success","follow",200), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse("fail","follow",500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    @DeleteMapping
    public ResponseEntity<ApiResponse> unfollowUser(@RequestBody Follow follow) {
        try {
            boolean res = followService.removeFollow(follow);
            if (!res) {
                return new ResponseEntity<>(new ApiResponse("fail","unfollow",400), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<> (new ApiResponse("success","unfollow",200), HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(new ApiResponse("fail","follow",500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/check")
    public ResponseEntity<?> checkFollow(@RequestBody Follow follow) {
        try{
            boolean isFollow = followService.checkFollow(follow);
            return new ResponseEntity<>(isFollow, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 특정 사용자의 팔로우 목록 조회
    @GetMapping("/followers/{userId}")
    public ResponseEntity<?> getFollowers(@PathVariable int userId) {
        try{
            List<User> followers = followService.getFollowerList(userId);
            return new ResponseEntity<>(followers, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
	
    // 특정 사용자의 팔로잉 목록 조회
    @GetMapping("/following/{userId}")
    public ResponseEntity<?> getFollowing(@PathVariable int userId) {
        try{
            List<User> following = followService.getFollowingList(userId);
            return new ResponseEntity<>(following, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
