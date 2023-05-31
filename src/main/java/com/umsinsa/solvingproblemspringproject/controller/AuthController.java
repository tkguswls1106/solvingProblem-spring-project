package com.umsinsa.solvingproblemspringproject.controller;

import com.umsinsa.solvingproblemspringproject.dto.token.TokenDto;
import com.umsinsa.solvingproblemspringproject.dto.user.*;
import com.umsinsa.solvingproblemspringproject.service.auth.AuthService;
import com.umsinsa.solvingproblemspringproject.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class AuthController {

    private final AuthService authService;


    @GetMapping("/auth")  // 헤더 토큰정보 불필요함 X
    public UserIdResponseDto isLogin() {  // 로그인 상태 여부 확인
        Long userId = SecurityUtil.getCurrentMemberId();
        UserIdResponseDto userIdResponseDto = new UserIdResponseDto(userId);

        return userIdResponseDto;
    }

    @PostMapping("/signup")  // 헤더 토큰정보 불필요함 X
    public UserResponseDto joinUser(@RequestBody UserSignupRequestDto userSignupRequestDto) {  // 회원가입
        UserResponseDto userResponseDto = authService.signup(userSignupRequestDto);
        return userResponseDto;
    }

    @PostMapping("/login")  // 헤더 토큰정보 불필요함 X
    public TokenDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {  // 로그인
        TokenDto tokenDto = authService.login(userLoginRequestDto);
        return tokenDto;
    }

    @PutMapping("/password")  // 헤더 토큰정보 필요함 O
    public void updatePassword(@RequestBody UserUpdatePwRequestDto userUpdatePwRequestDto) {  // 비밀번호 수정
        authService.updatePw(userUpdatePwRequestDto);
    }
}
