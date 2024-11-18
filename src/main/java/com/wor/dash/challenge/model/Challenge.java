package com.wor.dash.challenge.model;

// JYL : 챌린지
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Challenge {
	private int challengeId;
	@NonNull int challengeCategoryCode;
	@NonNull private String challengeTitle;
	@NonNull private String challengeDescription;
	private int challengeAuthorId;
	private String challengeCreateDate;
	private String challengeDeleteDate;
	private int challengeIsEnded;
}