package com.umsinsa.solvingproblemspringproject.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umsinsa.solvingproblemspringproject.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private Integer recommendCount;
    private List<Long> recommendUsers;

    @JsonIgnore
    public List<Long> getRecommendUserList(String recommendUsers) {
        String beforeParsing = recommendUsers;
        String[] afterParsing = beforeParsing.split("p");

        List<Long> userIds = new ArrayList<>();
        for (String userId : afterParsing) {
            userIds.add(Long.parseLong(userId));
        }

        return userIds;
    }


    // DB에서 repository를 통해 조회하거나 가져온 entity(도메인)를 dto로 변환 용도
    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.recommendCount = entity.getRecommendCount();
        this.recommendUsers = getRecommendUserList(entity.getRecommendUsers());
    }
}
