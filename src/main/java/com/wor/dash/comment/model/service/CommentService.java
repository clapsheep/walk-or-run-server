package com.wor.dash.comment.model.service;

import java.util.List;

import com.wor.dash.comment.model.Comment;

public interface CommentService {
	void addComment(Comment comment);
	List<Comment> getAllComment();
	boolean editComment(Comment comment);
	boolean removeComment(int commentId);
}
