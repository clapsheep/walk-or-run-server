package com.wor.dash.password.model.service;

import com.wor.dash.password.model.PasswordAnswer;
import com.wor.dash.password.model.PasswordFindQnA;
import com.wor.dash.password.model.PasswordQuestion;
import com.wor.dash.password.model.mapper.PasswordMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public
class PasswordServiceImpl implements PasswordService {

    private final PasswordMapper passwordMapper;

    @Override
    public
    Optional<List<PasswordQuestion>> allQuestions() {
        List<PasswordQuestion> questionList = passwordMapper.selectAllQuestions();
        if(questionList != null && !questionList.isEmpty()) {
            return Optional.ofNullable(questionList);
        } else {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public
    Optional<PasswordQuestion> getQuestion(int questionId) {
        return Optional.ofNullable(passwordMapper.selectQuestionByQuestionId(questionId));
    }

    @Override
    public
    Optional<PasswordFindQnA> getQnA(int userId) {
        return Optional.ofNullable(passwordMapper.selectQnAByUserId(userId));
    }

    @Override
    public
    Optional<Integer> addAnswer(PasswordAnswer passwordAnswer) {
        return Optional.of(passwordMapper.insertPasswordAnswer(passwordAnswer));
    }

    @Override
    public Optional<Integer> updateAnswer(PasswordAnswer passwordAnswer) {
        return Optional.of(passwordMapper.updatePasswordAnswer(passwordAnswer));
    }
}
