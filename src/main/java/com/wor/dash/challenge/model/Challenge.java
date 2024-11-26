package com.wor.dash.challenge.model;

import com.wor.dash.user.model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Challenge {
    @NonNull
    private int challengeCategoryCode;
    private String challengeCategoryName;
    private int challengeId;
    @NonNull
    private String challengeTitle;
    @NonNull
    private String challengeDescription;
    @NonNull
    private int challengeAuthorId;
    @NonNull
    private int challengeParticipantCnt;
    private String challengeCreateDate;
    private String challengeDeleteDate;
    private int challengeIsEnded;
    @NonNull
    private int challengeTargetCnt;
    private int challengeSchedulerCycle;
    private String dDay;
    private List<User> challengeParticipants;
}