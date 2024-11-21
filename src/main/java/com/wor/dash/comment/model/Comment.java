package com.wor.dash.comment.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentId;
    @NonNull
    private int challengeId;
    @NonNull
    private String commentContent;
    @NonNull
    private int commentAuthorId;
    private String commentCreateDate;
    private String commentAuthorName;
}
