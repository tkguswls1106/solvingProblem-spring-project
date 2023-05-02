package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemUpdateRequestDto {

    private String content;
    private String modificationPw;

    @Builder
    public ProblemUpdateRequestDto(String content, String modificationPw) {
        this.content = content;
        this.modificationPw = modificationPw;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Problem toEntity() {
        return Problem.ProblemUpdateBuilder()
                .content(content)
                .modificationPw(modificationPw)
                .build();
    }
}
