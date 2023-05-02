package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSaveRequestDto {

    private Integer type;
    private String content;
    private String modificationPw;

    @Builder
    public ProblemSaveRequestDto(Integer type, String content, String modificationPw) {
        this.type = type;
        this.content = content;
        this.modificationPw = modificationPw;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Problem toEntity() {
        return Problem.builder()
                .type(type)
                .content(content)
                .modificationPw(modificationPw)
                .build();
    }
}
