package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSaveResponseDto {

    private Long id;
    private Integer type;
    private String title;
    private String content;
    private String password;
    private Category category;

    // entity(도메인)를 dto로 변환 용도
    public ProblemSaveResponseDto(Problem entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.title = entity.getTitle();
        this.content = "가독성을 위해 문제내용은 생략하여 응답함.";
        this.password = "보안을 위해 비밀번호는 생략하여 응답함.";
        this.category = Category.builder()
                .id(entity.getCategory().getId())
                .name(entity.getCategory().getName())
                .image(entity.getCategory().getImage())
                .build();
    }
}
