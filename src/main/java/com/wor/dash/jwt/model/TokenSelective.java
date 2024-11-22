package com.wor.dash.jwt.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public
class TokenSelective extends Token{
    private Integer tokenId;
    private int userId;
    private String accessToken;
    private String refreshToken;
    private int logout;
    private int cond;
}
