package com.wor.dash.password;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class PasswordChangeUtil {
    private int userId;
    private String userPassword;
    private String newPassword;
    private String userPasswordAnswer;
}
