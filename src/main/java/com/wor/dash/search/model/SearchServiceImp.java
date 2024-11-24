package com.wor.dash.search.model;

import com.wor.dash.pageInfo.model.PageInfo;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.user.model.User;
import com.wor.dash.user.model.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SearchServiceImp implements SearchService {
    private final UserMapper userMapper;

    @Override
    public PageResponse<User> searchUser(String type, String value, int currentPage, int pageSize) {
        log.info("SearchService/searchUser");
        if (type.equals("email") || type.equals("nickname")) {
            int offset = (currentPage - 1) * pageSize;
            List<User> users = userMapper.selectUserListForSearch(type, value, offset, pageSize);
            int totalElements = userMapper.countSearchUser(type, value);
            PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
            return new PageResponse<>(users, pageInfo);
        }
        return null;
    }
}
