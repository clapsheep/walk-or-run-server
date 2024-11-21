package com.wor.dash.password.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public
class PasswordFindQnA {
    private int questionId;
    private String questionDescription;
    private int passwordAnswerId;
    private int userId;
    private String passwordQuestionAnswer;
}
