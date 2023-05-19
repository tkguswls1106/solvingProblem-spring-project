package com.umsinsa.solvingproblemspringproject.dto.user;

import com.umsinsa.solvingproblemspringproject.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String loginId;
    private String username;
    private Integer solvableCount;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.loginId = entity.getLoginId();
        this.username = entity.getUsername();
        this.solvableCount = entity.getSolvableCount();
    }
}