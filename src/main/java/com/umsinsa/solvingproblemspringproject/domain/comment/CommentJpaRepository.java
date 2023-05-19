package com.umsinsa.solvingproblemspringproject.domain.comment;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    void deleteAllByProblem(Problem problem);
    void deleteAllByUser(User user);
    List<Comment> findAllByProblem(Problem problem);  // 해당 문제의 모든 댓글 반환.
}
