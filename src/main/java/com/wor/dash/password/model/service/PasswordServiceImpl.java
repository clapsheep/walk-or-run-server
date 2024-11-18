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

        return Optional.empty();
    }

    @Override
    public
    Optional<PasswordQuestion> getQuestion(int questionId) {
        return Optional.empty();
    }

    @Override
    public
    Optional<PasswordFindQnA> getQnA(int userId) {
        return Optional.empty();
    }

    @Override
    public
    Optional<Integer> addAnswer(PasswordAnswer passwordAnswer) {
        return Optional.empty();
    }
}
