package com.wor.dash.comment.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wor.dash.comment.model.Comment;

@Mapper
public interface CommentMapper {
	void insertComment(Comment comment);
	List<Comment> selectCommentList();
	int updateComment(Comment comment);
	int deleteComment(int commentId);
}
