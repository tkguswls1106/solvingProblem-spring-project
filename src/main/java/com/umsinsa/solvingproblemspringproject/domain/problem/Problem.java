package com.umsinsa.solvingproblemspringproject.domain.problem;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "problem")
@Entity
public class Problem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Column(name = "problem_type", unique = true)
    private Integer type;
    // 객관식 문제: 1, 빈칸 문제: 2, 서술형 문제: 3

    @Column(name = "content", columnDefinition = "MEDIUMTEXT default null")
    private String content;

    @Column(name = "problem_password")  // 수정 및 삭제용 비밀번호 (문제 출제자 식별용도)
    private String password;


    @Builder
    public Problem(Long id, Integer type, String content, String password) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.password = password;
    }

    @Builder(builderClassName = "ProblemUpdateBuilder", builderMethodName = "ProblemUpdateBuilder")
    public Problem(String content, String password) {
        // 이 빌더는 문제 수정때만 사용할 용도
        this.content = content;
        this.password = password;
    }

    @Builder(builderClassName = "ProblemDeleteBuilder", builderMethodName = "ProblemDeleteBuilder")
    public Problem(String password) {
        // 이 빌더는 문제 삭제때만 사용할 용도
        this.password = password;
    }


    // 수정(업데이트) 기능
    public void updateContent(String content) {  // 문제 내용 변경 기능
        this.content = content;
    }
}
