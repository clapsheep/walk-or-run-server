package com.wor.dash.search.model;

import com.wor.dash.pageInfo.model.PageInfo;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.user.model.User;
import com.wor.dash.user.model.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchServiceImp implements SearchService {
    private final UserMapper userMapper;

    @Override
    public PageResponse<User> searchUser(String type, String value, int currentPage, int pageSize) {
        if (type.equals("email") || type.equals("nickname")) {
            List<User> users = userMapper.selectUserListForSearch(type, value, currentPage, pageSize);
            int totalElements = users.size();
            int offset = (currentPage - 1) * pageSize;
            PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
            return new PageResponse<>(users, pageInfo);
        }
        return null;
    }
}
