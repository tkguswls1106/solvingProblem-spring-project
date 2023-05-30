package com.umsinsa.solvingproblemspringproject.dto.user;

import com.umsinsa.solvingproblemspringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdatePwRequestDto {

    private String loginId;
    private String loginPw;
    private String newLoginPw;

    @Builder
    public UserUpdatePwRequestDto(String loginId, String loginPw, String newLoginPw) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.newLoginPw = newLoginPw;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public User toEntity() {
        return User.UserUpdatePwBuilder()
                .loginId(loginId)
                .newLoginPw(newLoginPw)
                .build();
    }
}
