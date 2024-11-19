package com.wor.dash.comment.model.service;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.pageInfo.model.PageResponse;

public interface CommentService {
    void addComment(Comment comment);

    PageResponse<Comment> getCommentList(int challengeId, int currentPage, int pageSize);

    boolean editComment(Comment comment);

    boolean removeComment(int commentId);

    int countComment(int challengeId);
}
