package com.wor.dash.password.model.service;

import com.wor.dash.password.model.PasswordAnswer;
import com.wor.dash.password.model.PasswordFindQnA;
import com.wor.dash.password.model.PasswordQuestion;
import com.wor.dash.password.model.mapper.PasswordMapper;
import com.wor.dash.user.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public
class PasswordServiceImpl implements PasswordService {

    private final PasswordMapper passwordMapper;

    @Override
    public
    Optional<Map<Integer, String>> allQuestions() {
        log.info("PasswordService/allQuestions");
        Map<Integer, String> map = new HashMap<>();
        List<PasswordQuestion> pq = passwordMapper.selectAllQuestions();
        for(PasswordQuestion q: pq) {
            map.put(q.getQuestionId(), q.getQuestionDescription());
        }
        if(pq != null && !pq.isEmpty()) {
            return Optional.ofNullable(map);
        } else {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public
    Optional<PasswordQuestion> getQuestion(int questionId) {
        log.info("PasswordService/getQuestion");
        return Optional.ofNullable(passwordMapper.selectQuestionByQuestionId(questionId));
    }

    @Override
    public
    Optional<PasswordFindQnA> getQnA(int userId) {
        log.info("PasswordService/getQnA");
        return Optional.ofNullable(passwordMapper.selectQnAByUserId(userId));
    }

    @Override
    public
    Optional<Integer> addAnswer(PasswordAnswer passwordAnswer) {
        log.info("PasswordService/addAnswer");
        return Optional.of(passwordMapper.insertPasswordAnswer(passwordAnswer));
    }

    @Override
    public Optional<Integer> updateAnswer(PasswordAnswer passwordAnswer) {
        log.info("PasswordService/updateAnswer");
        return Optional.of(passwordMapper.updatePasswordAnswer(passwordAnswer));
    }

    @Override
    public Optional<Integer> findPassword(User user) {
        log.info("PasswordService/findPassword");
        return Optional.ofNullable(passwordMapper.selectUserIdByQnA(user));
    }

}
