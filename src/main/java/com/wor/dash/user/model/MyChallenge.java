package com.wor.dash.user.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyChallenge {
    private int challengeId;
    private String challengeCategoryName;
    private String challengeTitle;
    private String challengeDescription;
    private int challengeParticipantCnt;
    private String author;
    private String dDay;
}