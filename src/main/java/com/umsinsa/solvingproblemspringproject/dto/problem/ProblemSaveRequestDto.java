package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSaveRequestDto {

    private Integer type;
    private String title;
    private String content;
    private String password;
    private Category category;

    @Builder
    public ProblemSaveRequestDto(Integer type, String title, String content, String password, Category category) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.password = password;
        this.category = category;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Problem toEntity() {
        return Problem.builder()
                .type(type)
                .title(title)
                .content(content)
                .password(password)
                .category(category)
                .build();
    }
}
