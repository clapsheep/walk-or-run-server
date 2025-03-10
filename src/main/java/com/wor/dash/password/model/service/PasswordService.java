package com.wor.dash.password.model.service;

import com.wor.dash.password.PasswordChangeUtil;
import com.wor.dash.password.model.PasswordAnswer;
import com.wor.dash.password.model.PasswordFindQnA;
import com.wor.dash.password.model.PasswordQuestion;
import com.wor.dash.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public
interface PasswordService {

    Optional<Map<Integer, String>> allQuestions();
    Optional<PasswordQuestion> getQuestion(int questionId);
    Optional<PasswordFindQnA> getQnA(int userId);
    Optional<Integer> addAnswer(PasswordAnswer passwordAnswer);
    Optional<Integer> updateAnswer(PasswordAnswer passwordAnswer);
    Optional<Integer> findPassword(User user);
    boolean checkUserPassword(PasswordChangeUtil passwordChangeUtil);

}
