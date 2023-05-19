package com.umsinsa.solvingproblemspringproject.dto.problem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProblemOneResponseDto {

    private Long id;
    private Integer type;
    private String title;
    private String content;
    private Integer recommendCount;
    private List<Long> recommendUsers;
    private List<Long> solveUsers;
    private Integer isSolve;

    private User user;
    private Category category;


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

    @JsonIgnore
    public List<Long> getSolveUserList(String solveUsers) {
        String beforeParsing = solveUsers;
        String[] afterParsing = beforeParsing.split("p");

        List<Long> userIds = new ArrayList<>();
        for (String userId : afterParsing) {
            userIds.add(Long.parseLong(userId));
        }

        return userIds;
    }

    @JsonIgnore
    public Integer getIsSolve(Long userId, List<Long> userIds) {
        boolean isContain = userIds.contains(userId);
        if (isContain) {
            return 1;
        }
        else {
            return 0;
        }
    }


    // DB에서 repository를 통해 조회하거나 가져온 entity(도메인)를 dto로 변환 용도
    public ProblemOneResponseDto(Problem entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.recommendCount = entity.getRecommendCount();
        this.recommendUsers = getRecommendUserList(entity.getRecommendUsers());
        this.solveUsers = getSolveUserList(entity.getSolveUsers());
        this.isSolve = getIsSolve(entity.getUser().getId(), getSolveUserList(entity.getSolveUsers()));

        this.user = User.builder()
                .id(entity.getUser().getId())
                .loginId(entity.getUser().getLoginId())
                .username(entity.getUser().getUsername())
                .solvableCount(entity.getUser().getSolvableCount())
                .build();
        this.category = Category.builder()
                .id(entity.getCategory().getId())
                .name(entity.getCategory().getName())
                .image(entity.getCategory().getImage())
                .build();
    }
}
