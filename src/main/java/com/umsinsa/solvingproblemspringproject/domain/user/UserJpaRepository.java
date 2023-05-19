package com.umsinsa.solvingproblemspringproject.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// jpa 레포지토리는 @Repository 어노테이션 안적어도됨.
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);  // 로그인아이디로 사용자 1명 검색하기.
}
