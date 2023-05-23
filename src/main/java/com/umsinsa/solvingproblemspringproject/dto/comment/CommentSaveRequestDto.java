package com.umsinsa.solvingproblemspringproject.dto.comment;

import com.umsinsa.solvingproblemspringproject.domain.comment.Comment;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private String content;

    @Builder
    public CommentSaveRequestDto(String content) {
        this.content = content;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Comment toEntity(User user, Problem problem) {
        return Comment.builder()
                .content(content)

                .user(user)
                .problem(problem)
                .build();
    }
}
