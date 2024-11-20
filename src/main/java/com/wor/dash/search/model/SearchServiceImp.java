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
        List<User> res;
        if (type.equals("email") && value.contains("nickname")) {
            return null;
            //return userMapper.getUsersForSearch(type, value);
        }
        return null;
    }
}
