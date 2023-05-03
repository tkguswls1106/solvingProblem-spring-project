package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemDeleteRequestDto {

    private String password;

    @Builder
    public ProblemDeleteRequestDto(String password) {
        this.password = password;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Problem toEntity() {
        return Problem.ProblemDeleteBuilder()
                .password(password)
                .build();
    }
}
