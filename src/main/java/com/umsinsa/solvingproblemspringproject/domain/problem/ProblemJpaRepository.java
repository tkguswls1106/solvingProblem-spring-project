package com.umsinsa.solvingproblemspringproject.domain.problem;

import org.springframework.data.jpa.repository.JpaRepository;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface ProblemJpaRepository extends JpaRepository<Problem, Long> {

}