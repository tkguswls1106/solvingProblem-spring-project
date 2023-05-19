package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSaveRequestDto {

    private Integer type;
    private String title;
    private String content;

    @Builder
    public ProblemSaveRequestDto(Integer type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Problem toEntity(User user, Category category) {
        return Problem.builder()
                .type(type)
                .title(title)
                .content(content)

                .user(user)
                .category(category)
                .build();
    }
}
