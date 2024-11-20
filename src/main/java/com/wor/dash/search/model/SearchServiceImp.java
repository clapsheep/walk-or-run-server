package com.wor.dash.search.model;

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
    public List<User> searchUser(String type, String value) {
        if (type.equals("email") || type.equals("nickname")) {
            return userMapper.getUserListForSearch(type, value);
        }
        return null;
    }
}
