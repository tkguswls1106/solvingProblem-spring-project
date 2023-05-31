package com.umsinsa.solvingproblemspringproject.service.logic;

import com.umsinsa.solvingproblemspringproject.domain.comment.CommentJpaRepository;
import com.umsinsa.solvingproblemspringproject.domain.problem.ProblemJpaRepository;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import com.umsinsa.solvingproblemspringproject.domain.user.UserJpaRepository;
import com.umsinsa.solvingproblemspringproject.dto.user.UserResponseDto;
import com.umsinsa.solvingproblemspringproject.dto.user.UserUpdateSolvableRequestDto;
import com.umsinsa.solvingproblemspringproject.service.UserService;
import com.umsinsa.solvingproblemspringproject.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class UserServiceLogic implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final ProblemJpaRepository problemJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto getMyInfoBySecurity() {  // 헤더에 있는 token값을 토대로 User의 data를 건내주는 메소드이다.
        return userJpaRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::new)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }


    @Transactional(readOnly = true)
    @Override
    public UserResponseDto findById(Long userId) {  // userId로 검색한 사용자 1명 반환 기능.

        getMyInfoBySecurity();  // 헤더의 토큰 유효검사와 사용자 일치 검사 실행.

        User entity = userJpaRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 userId의 사용자 조회 실패"));
        return new UserResponseDto(entity);
    }

    @Transactional
    @Override
    public void updateSolvable(Long userId, UserUpdateSolvableRequestDto userUpdateSolvableRequestDto) {  // 해당 userId 사용자의 문제풀이가능잔여횟수 수정 기능.

        getMyInfoBySecurity();  // 헤더의 토큰 유효검사와 사용자 일치 검사 실행.

        User entity = userJpaRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 userId의 사용자 조회 실패"));

        Integer result_count = entity.getSolvableCount() + userUpdateSolvableRequestDto.getSolvableCount();
        if (result_count < 0) {
            throw new RuntimeException("ERROR - 이미 남은 잔여문제풀이가횟수가 0개 입니다.");
        }
        else {
            entity.updateSolvableCount(result_count);  // 프론트에서 -1이든 +1이든 보내주면 그저 백엔드에서는 그값을 기존의 카운트에서 더해주기만 하면됨.
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {  // 해당 userId의 사용자 삭제 기능. 동시에 해당 사용자의 댓글과 문제도 함께 삭제함.

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.
        if (securityUserResponseDto.getId() != userId) {
            // 헤더 token의 userId와 패스파라미터의 userId가 일치하는지 확인하여,
            // 회원 탈퇴 접근 권한이 있는 본인이 맞는지 먼저 확인한다.
            throw new RuntimeException("ERROR - 해당 사용자를 회원 탈퇴할 권한이 없습니다.");
        }

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 userId의 사용자 조회 실패"));

        commentJpaRepository.deleteAllByUser(userEntity);  // 자식 테이블인 comment를 먼저 삭제.
        problemJpaRepository.deleteAllByUser(userEntity);  // 그다음 자식 테이블인 problem을 먼저 삭제.
        userJpaRepository.delete(userEntity);  // 최종적으로 부모테이블인 user 삭제.
    }
}
