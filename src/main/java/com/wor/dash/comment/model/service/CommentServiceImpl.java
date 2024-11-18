package com.wor.dash.comment.model.service;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.comment.model.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public void addComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public List<Comment> getAllComment() {
        return commentMapper.selectCommentList();
    }

    @Override
    public boolean editComment(Comment comment) {
        int result = commentMapper.updateComment(comment);

        return result == 1;
    }

    @Override
    public boolean removeComment(int commentId) {
        int result = commentMapper.deleteComment(commentId);

        return result == 1;
    }

}
