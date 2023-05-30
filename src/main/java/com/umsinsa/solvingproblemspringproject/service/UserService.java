package com.umsinsa.solvingproblemspringproject.service;

import com.umsinsa.solvingproblemspringproject.dto.user.UserResponseDto;
import com.umsinsa.solvingproblemspringproject.dto.user.UserUpdateSolvableRequestDto;

public interface UserService {

    UserResponseDto findById(Long userId);  // userId로 검색한 사용자 1명 반환 기능.
    void updateSolvable(Long userId, UserUpdateSolvableRequestDto userUpdateSolvableRequestDto);  // 해당 userId 사용자의 문제풀이가능잔여횟수 수정 기능.
    void deleteUser(Long userId);  // 해당 userId의 사용자 삭제 기능. 동시에 해당 사용자의 댓글과 문제도 함께 삭제함.
}
