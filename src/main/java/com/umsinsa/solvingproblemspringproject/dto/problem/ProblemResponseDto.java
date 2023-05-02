package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemResponseDto {

    private Long id;
    private Integer type;
    private String content;

    // DB에서 repository를 통해 조회하거나 가져온 entity(도메인)를 dto로 변환 용도
    public ProblemResponseDto(Problem entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.content = entity.getContent();
    }
}
