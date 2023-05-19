package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemUpdateRequestDto {

    private String title;
    private String content;

    @Builder
    public ProblemUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Problem toEntity() {
        return Problem.ProblemUpdateBuilder()
                .title(title)
                .content(content)
                .build();
    }
}
