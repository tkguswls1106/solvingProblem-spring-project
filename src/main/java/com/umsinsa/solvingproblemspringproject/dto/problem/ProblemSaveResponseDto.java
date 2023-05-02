package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSaveResponseDto {

    private Long id;
    private Integer type;
    private String content;
    private String modificationPw;

    // entity(도메인)를 dto로 변환 용도
    public ProblemSaveResponseDto(Problem entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.content = "가독성을 위해 문제내용은 생략하여 응답함.";
        this.modificationPw = "비밀번호 유출 방지를 위해 수정비번은 생략하여 응답함.";
    }
}
