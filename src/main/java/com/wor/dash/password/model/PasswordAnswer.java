package com.wor.dash.password.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public
class PasswordAnswer {
    private int passwordAnswerId;
    private int userId;
    private int questionId;
    private String passwordQuestionAnswer;
}
