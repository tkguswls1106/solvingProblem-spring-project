package com.umsinsa.solvingproblemspringproject.controller;

import com.umsinsa.solvingproblemspringproject.dto.user.UserResponseDto;
import com.umsinsa.solvingproblemspringproject.dto.user.UserUpdateSolvableRequestDto;
import com.umsinsa.solvingproblemspringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{userId}")  // 헤더 토큰정보 필요함 O
    public UserResponseDto findUserById(@PathVariable Long userId) {  // 회원정보 조회
        UserResponseDto userResponseDto = userService.findById(userId);
        return userResponseDto;
    }

    @PutMapping("/{userId}")  // 헤더 토큰정보 필요함 O
    public void updateSolvable(@PathVariable Long userId, @RequestBody UserUpdateSolvableRequestDto userUpdateSolvableRequestDto) {  // 잔여문제풀이가능횟수 수정
        userService.updateSolvable(userId, userUpdateSolvableRequestDto);
    }

    @DeleteMapping("/{userId}")  // 헤더 토큰정보 필요함 O
    public void deleteUser(@PathVariable Long userId) {  // 회원 탈퇴
        userService.deleteUser(userId);
    }

}
