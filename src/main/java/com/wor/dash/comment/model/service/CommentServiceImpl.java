package com.wor.dash.comment.model.service;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.comment.model.mapper.CommentMapper;
import com.wor.dash.pageInfo.model.PageInfo;
import com.wor.dash.pageInfo.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public void addComment(int challengeId, Comment comment) {
        commentMapper.insertComment(challengeId, comment);
    }

    @Override
    public PageResponse<Comment> getCommentList(int challengeId, int currentPage, int pageSize) {
        int totalElements = commentMapper.countComments(challengeId);
        int offset = (currentPage - 1) * pageSize;
        List<Comment> comments = commentMapper.selectCommentList(challengeId, offset, pageSize);
        PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
        return new PageResponse<>(comments, pageInfo);
    }

    @Override
    public boolean editComment(int challengeId, int commentId, Comment comment) {
        int result = commentMapper.updateComment(challengeId, commentId, comment);
        return result == 1;
    }

    @Override
    public boolean removeComment(int challengeId, int commentId) {
        int result = commentMapper.deleteComment(challengeId, commentId);

        return result == 1;
    }

    @Override
    public int countComment(int challengeId) {
        return commentMapper.countComments(challengeId);
    }

}
