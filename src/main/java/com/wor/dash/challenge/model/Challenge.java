package com.wor.dash.challenge.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Challenge {
    @NonNull
    int challengeCategoryCode;
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
}
