package com.wor.dash.search.model;

import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.user.model.User;

public interface SearchService {
    PageResponse<User> searchUser(String type, String value, int currentPage, int pageSize);
}
