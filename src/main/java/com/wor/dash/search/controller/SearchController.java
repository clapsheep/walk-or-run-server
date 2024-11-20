package com.wor.dash.search.controller;

import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.response.ApiResponse;
import com.wor.dash.search.model.SearchService;
import com.wor.dash.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@AllArgsConstructor
@Tag(name = "Search Controller", description = "각종 서비스 내에 필요한 검색 기능을 관리합니다.")
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "사용자 검색", description = "이메일 또는 닉네임으로 사용자를 검색합니다.")
    @GetMapping("/user")
    public ResponseEntity<?> searchUser(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String key, @RequestParam String value) {
        try {
            PageResponse<User> res = searchService.searchUser(key, value, page, size);
            if (res.getContent().isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("empty", "searchUser", 204), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid search type");
        }
    }
}