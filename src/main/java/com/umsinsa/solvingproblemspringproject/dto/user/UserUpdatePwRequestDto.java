package com.umsinsa.solvingproblemspringproject.dto.user;

import com.umsinsa.solvingproblemspringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdatePwRequestDto {

    private String newLoginPw;

    @Builder
    public UserUpdatePwRequestDto(String newLoginPw) {
        this.newLoginPw = newLoginPw;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public User toEntity() {
        return User.UserUpdatePwBuilder()
                .newLoginPw(newLoginPw)
                .build();
    }
}
