package com.umsinsa.solvingproblemspringproject.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

    private String loginId;
    private String loginPw;

    @Builder
    public UserLoginRequestDto(String loginId, String loginPw) {
        this.loginId = loginId;
        this.loginPw = loginPw;
    }


    // UsernamePasswordAuthenticationToken을 반환하여 아이디와 비밀번호가 일치하는지 검증하는 로직을 넣을 수 있게된다.
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, loginPw);
    }
}
