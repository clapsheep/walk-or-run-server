package com.wor.dash.pageInfo.model;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {
    private List<T> content;
    private PageInfo pageInfo;

    public PageResponse(List<T> content, PageInfo pageInfo) {
        this.content = content;
        this.pageInfo = pageInfo;
    }
}
