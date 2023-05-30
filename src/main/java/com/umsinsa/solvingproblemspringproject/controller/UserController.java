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


    @GetMapping("/{userId}")
    public UserResponseDto findUserById(@PathVariable Long userId) {  // 회원정보 조회
        UserResponseDto userResponseDto = userService.findById(userId);
        return userResponseDto;
    }

    @PutMapping("/{userId}")
    public void updateSolvable(@PathVariable Long userId, UserUpdateSolvableRequestDto userUpdateSolvableRequestDto) {  // 잔여문제풀이가능횟수 수정
        // 근데 이게 error이나 exception을 반환해서 프론트에 전달해야하는데, void 형식으로 반환해도 되는지는 모르겠음. 미확인 상태임.

        userService.updateSolvable(userId, userUpdateSolvableRequestDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

}
