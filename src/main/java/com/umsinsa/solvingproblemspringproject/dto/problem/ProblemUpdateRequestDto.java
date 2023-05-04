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
    private String password;

    @Builder
    public ProblemUpdateRequestDto(String title, String content, String password) {
        this.title = title;
        this.content = content;
        this.password = password;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Problem toEntity() {
        return Problem.ProblemUpdateBuilder()
                .title(title)
                .content(content)
                .password(password)
                .build();
    }
}
