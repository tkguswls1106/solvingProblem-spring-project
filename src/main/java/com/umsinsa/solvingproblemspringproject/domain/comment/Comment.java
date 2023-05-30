package com.umsinsa.solvingproblemspringproject.domain.comment;

import com.umsinsa.solvingproblemspringproject.domain.DefaultCommentEntity;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "comment")
@Entity
public class Comment extends DefaultCommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "recommend_users", columnDefinition = "TEXT default null")
    private String recommendUsers;
    // 파싱법: 1p2p...


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;


    @Builder
    public Comment(Long id, String content, User user, Problem problem) {
        this.id = id;
        this.content = content;

        this.user = user;
        this.problem = problem;
    }

    @Builder(builderClassName = "CommentUpdateBuilder", builderMethodName = "CommentUpdateBuilder")
    public Comment(String content) {
        // 이 빌더는 댓글 수정때만 사용할 용도
        this.content = content;
    }


    // 수정(업데이트) 기능
    public void updateComment(String content) {  // 댓글 변경 기능
        this.content = content;
    }
    public void updateRecommend(Integer recommendCount, String recommendUsers) {  // 댓글 추천시 업데이트 기능
        this.recommendCount = recommendCount;
        this.recommendUsers = recommendUsers;
    }
}
