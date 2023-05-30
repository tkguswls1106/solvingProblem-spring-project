package com.umsinsa.solvingproblemspringproject.domain.problem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umsinsa.solvingproblemspringproject.domain.DefaultProblemEntity;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor

@Table(name = "problem")
@Entity
public class Problem extends DefaultProblemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "problem_type")
    private Integer type;
    // 객관식 문제: 1, 빈칸 문제: 2, 서술형 문제: 3

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "MEDIUMTEXT default null")
    private String content;

    @Column(name = "recommend_users", columnDefinition = "TEXT default null")
    private String recommendUsers;
    // 파싱법: 1p2p...

    @Column(name = "solve_users", columnDefinition = "TEXT default null")
    private String solveUsers;
    // 파싱법: 1p2p...


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Builder
    public Problem(Long id, Integer type, String title, String content, User user, Category category) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;

        this.user = user;
        this.category = category;
    }

    @Builder(builderClassName = "ProblemUpdateBuilder", builderMethodName = "ProblemUpdateBuilder")
    public Problem(String title, String content) {
        // 이 빌더는 문제의 제목과 내용 수정때만 사용할 용도
        this.title = title;
        this.content = content;
    }


    // 수정(업데이트) 기능
    public void updateProblem(String title, String content) {  // 문제 내용 변경 기능
        this.title = title;
        this.content = content;
    }
    public void updateRecommend(Integer recommendCount, String recommendUsers) {  // 문제 추천시 업데이트 기능
        this.recommendCount = recommendCount;
        this.recommendUsers = recommendUsers;
    }
    public void updateSolve(String solveUsers) {  // 문제 풀경우 푼사용자목록 업데이트 기능
        this.solveUsers = solveUsers;
    }
}
