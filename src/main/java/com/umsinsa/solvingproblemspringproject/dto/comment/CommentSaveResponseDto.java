package com.umsinsa.solvingproblemspringproject.dto.comment;

import com.umsinsa.solvingproblemspringproject.domain.comment.Comment;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveResponseDto {

    private Long id;
    private String content;

    private User user;
    private Problem problem;

    // entity(도메인)를 dto로 변환 용도
    public CommentSaveResponseDto(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();

        this.user = User.builder()
                .id(entity.getUser().getId())
                .loginId(entity.getUser().getLoginId())
                .username(entity.getUser().getUsername())
                .solvableCount(entity.getUser().getSolvableCount())
                .build();
        this.problem = Problem.builder()
                .id(entity.getProblem().getId())
                .type(entity.getProblem().getType())
                .title(entity.getProblem().getTitle())
                .content(entity.getProblem().getContent())

                .user(entity.getProblem().getUser())
                .category(entity.getProblem().getCategory())
                .build();
    }
}
