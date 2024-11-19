package com.wor.dash.comment.model.mapper;

import com.wor.dash.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(Comment comment);

    List<Comment> selectCommentList(@Param("challengeId") int challengeId, @Param("offset") int offset, @Param("pageSize") int limit);

    int updateComment(Comment comment);

    int deleteComment(int commentId);

    int countComments(int challengeId);
}
