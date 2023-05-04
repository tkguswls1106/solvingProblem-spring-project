package com.umsinsa.solvingproblemspringproject.domain.problem;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface ProblemJpaRepository extends JpaRepository<Problem, Long> {

    List<Problem> findAllByCategory(Category category);  // 해당 카테고리의 모든 문제 반환.
}
