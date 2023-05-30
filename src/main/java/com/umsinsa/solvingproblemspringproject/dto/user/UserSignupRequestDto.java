package com.umsinsa.solvingproblemspringproject.dto.user;

import com.umsinsa.solvingproblemspringproject.domain.user.Authority;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

    private String loginId;
    private String loginPw;
    private String username;

    @Builder
    public UserSignupRequestDto(String loginId, String loginPw, String username) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.username = username;
    }

    // 클라이언트에게 받아왔고 계층간 이동에 사용되는 dto를 DB에 접근할수있는 entity로 변환 용도
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.UserJoinBuilder()
                .loginId(loginId)
                .loginPw(passwordEncoder.encode(loginPw))
                .username(username)
                .solvableCount(5)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
