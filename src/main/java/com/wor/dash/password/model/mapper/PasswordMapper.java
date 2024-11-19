package com.wor.dash.password.model.mapper;

import com.wor.dash.password.model.PasswordAnswer;
import com.wor.dash.password.model.PasswordFindQnA;
import com.wor.dash.password.model.PasswordQuestion;

import java.util.List;

public
interface PasswordMapper {

    List<PasswordQuestion> selectAllQuestions();
    PasswordQuestion selectQuestionByQuestionId(int questionId);
    PasswordFindQnA selectQnAByUserId(int userId);
    int insertPasswordAnswer(PasswordAnswer passwordAnswer);
    int updatePasswordAnswer(PasswordAnswer passwordAnswer);

}
