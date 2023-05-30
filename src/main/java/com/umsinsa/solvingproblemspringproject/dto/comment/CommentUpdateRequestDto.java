package com.umsinsa.solvingproblemspringproject.dto.comment;

import com.umsinsa.solvingproblemspringproject.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

    private String content;

    @Builder
    public CommentUpdateRequestDto(String content) {
        this.content = content;
    }

    // dto를 DB에 접근할수있는 entity로 변환 용도
    public Comment toEntity() {
        return Comment.CommentUpdateBuilder()
                .content(content)
                .build();
    }
}
