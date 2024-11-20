package com.wor.dash.comment.model.service;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.pageInfo.model.PageResponse;

public interface CommentService {
    void addComment(int challengeId, Comment comment);

    PageResponse<Comment> getCommentList(int challengeId, int currentPage, int pageSize);

    boolean editComment(int challengeId, int commentId, Comment comment);

    boolean removeComment(int challengeId, int commentId);

    int countComment(int challengeId);
}
