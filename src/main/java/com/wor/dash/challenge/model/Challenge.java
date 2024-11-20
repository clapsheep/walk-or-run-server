package com.wor.dash.challenge.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Challenge {
    @NonNull
    private Integer challengeCategoryCode;
    @NonNull
    private String challengeCategoryName;
    private Integer challengeId;
    @NonNull
    private String challengeTitle;
    @NonNull
    private String challengeDescription;
    @NonNull
    private Integer challengeAuthorId;
    @NonNull
    private Integer challengeParticipantCnt;
    private String challengeCreateDate;
    private String challengeDeleteDate;
    private Integer challengeIsEnded;
    @NonNull
    private Integer challengeTargetCnt;
}
