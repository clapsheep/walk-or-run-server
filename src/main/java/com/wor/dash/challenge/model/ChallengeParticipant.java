package com.wor.dash.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// JYL : 챌린지 참여자
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ChallengeParticipant {
	private int participantId;
	@NonNull private int challengeId;
	@NonNull private int userId;
}
