package com.wor.dash.jwt.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public
class TokenSelective extends Token{
    private int logout;
    private int cond;
}
