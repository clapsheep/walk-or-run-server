package com.wor.dash.comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {
	private int commentId;
	@NonNull private int challengeId;
	@NonNull private String commentContent;
	@NonNull private int commentAuthorId;
	private String commentCreateDate;
}
