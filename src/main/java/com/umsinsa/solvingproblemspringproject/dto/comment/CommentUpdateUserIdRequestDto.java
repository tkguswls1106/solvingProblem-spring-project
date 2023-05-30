package com.umsinsa.solvingproblemspringproject.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateUserIdRequestDto {

    private Long userId;

    @Builder
    public CommentUpdateUserIdRequestDto(Long userId) {
        this.userId = userId;
    }
}
