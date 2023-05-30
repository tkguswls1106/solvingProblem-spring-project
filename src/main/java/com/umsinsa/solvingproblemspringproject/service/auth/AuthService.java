package com.umsinsa.solvingproblemspringproject.service.auth;

import com.umsinsa.solvingproblemspringproject.domain.user.User;
import com.umsinsa.solvingproblemspringproject.domain.user.UserJpaRepository;
import com.umsinsa.solvingproblemspringproject.dto.token.TokenDto;
import com.umsinsa.solvingproblemspringproject.dto.user.UserLoginRequestDto;
import com.umsinsa.solvingproblemspringproject.dto.user.UserResponseDto;
import com.umsinsa.solvingproblemspringproject.dto.user.UserSignupRequestDto;
import com.umsinsa.solvingproblemspringproject.dto.user.UserUpdatePwRequestDto;
import com.umsinsa.solvingproblemspringproject.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final AuthenticationManagerBuilder managerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    public UserResponseDto getMyInfoBySecurity() {  // 헤더에 있는 token값을 토대로 User의 data를 건내주는 메소드이다.
        return userJpaRepository.findById(com.umsinsa.solvingproblemspringproject.util.SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::new)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }


    @Transactional
    public UserResponseDto signup(UserSignupRequestDto userSignupRequestDto) {  // 신규 사용자 생성하고 user 반환 기능.
        // 클라이언트가 요청한, 클라이언트와 교류한 정보니까 RequestDto 형식을 파라미터로 받음.

        String newLoginId = userSignupRequestDto.getLoginId();
        userJpaRepository.findByLoginId(newLoginId)
                .ifPresent(user -> {  // 해당 로그인아이디의 사용자가 이미 존재한다면,
                    throw new RuntimeException("ERROR - 이미 존재하는 로그인아이디 입니다.");
                });

        User entity = userJpaRepository.save(userSignupRequestDto.toEntity(passwordEncoder));
        return new UserResponseDto(entity);
    }

    @Transactional
    public TokenDto login(UserLoginRequestDto userLoginRequestDto) {  // 로그인 기능.

        UsernamePasswordAuthenticationToken authenticationToken = userLoginRequestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        // 여기서 실제로 검증이 이루어진다.

        return tokenProvider.generateTokenDto(authentication);
    }

    @Transactional
    public void updatePw(UserUpdatePwRequestDto userUpdatePwRequestDto) {  // 사용자의 비밀번호 수정 기능.

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.

        User entity = User.builder()
                .id(securityUserResponseDto.getId())
                .loginId(securityUserResponseDto.getLoginId())
                .username(securityUserResponseDto.getUsername())
                .solvableCount(securityUserResponseDto.getSolvableCount())
                .build();

        entity.updateLoginPw(passwordEncoder.encode(userUpdatePwRequestDto.getNewLoginPw()));
    }
}
