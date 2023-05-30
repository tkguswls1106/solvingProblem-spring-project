package com.umsinsa.solvingproblemspringproject.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIdResponseDto {

    private Long id;

    @Builder
    public UserIdResponseDto(Long id) {
        this.id = id;
    }
}
