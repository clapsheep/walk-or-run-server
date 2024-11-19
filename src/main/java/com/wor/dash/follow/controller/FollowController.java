package com.wor.dash.follow.controller;

import java.util.List;

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
    public ResponseEntity<Void> followUser(@RequestBody Follow follow) {
        followService.addFollow(follow);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
	
    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable("followId") int followId) {
        followService.removeFollow(followId);
        return ResponseEntity.ok().build();
    }
    

    // 특정 사용자의 팔로우 목록 조회
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<Follow>> getFollowers(@PathVariable int userId) {
        List<Follow> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }
	
    // 특정 사용자의 팔로잉 목록 조회
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<Follow>> getFollowing(@PathVariable int userId) {
        List<Follow> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }
}
