package com.umsinsa.solvingproblemspringproject.dto.problem;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemUpdateUserIdRequestDto {

    private Long userId;

    @Builder
    public ProblemUpdateUserIdRequestDto(Long userId) {
        this.userId = userId;
    }
}
