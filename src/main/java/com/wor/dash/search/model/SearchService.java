package com.wor.dash.search.model;

import com.wor.dash.user.model.User;

import java.util.List;

public interface SearchService {
    List<User> searchUser(String type, String value);
}
