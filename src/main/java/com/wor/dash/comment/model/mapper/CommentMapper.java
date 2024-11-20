package com.wor.dash.comment.model.mapper;

import com.wor.dash.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(@Param("challengeId") int challengeId, @Param("comment") Comment comment);

    List<Comment> selectCommentList(@Param("challengeId") int challengeId, @Param("offset") int offset, @Param("pageSize") int limit);

    int updateComment(@Param("challengeId") int challengeId, @Param("commentId") int commentId, @Param("comment") Comment comment);

    int deleteComment(@Param("challengeId") int challengeId, @Param("commentId") int commentId);

    int countComments(@Param("challengeId") int challengeId);
}
