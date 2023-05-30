package com.umsinsa.solvingproblemspringproject.domain.user;

import com.umsinsa.solvingproblemspringproject.domain.DefaultUserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "user")
@Entity
public class User extends DefaultUserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", unique = true)
    private String loginId;

    @Column(name = "login_password")
    private String loginPw;

    @Column(name = "username")
    private String username;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public User(Long id, String loginId, String username, Integer solvableCount) {
        this.id = id;
        this.loginId = loginId;
        this.username = username;
        this.solvableCount = solvableCount;
    }

    @Builder(builderClassName = "UserJoinBuilder", builderMethodName = "UserJoinBuilder")
    public User(String loginId, String loginPw, String username, Integer solvableCount, Authority authority) {
        // 이 빌더는 사용자 회원가입때만 사용할 용도
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.username = username;
        this.solvableCount = solvableCount;
        this.authority = authority;
    }

    @Builder(builderClassName = "UserUpdatePwBuilder", builderMethodName = "UserUpdatePwBuilder")
    public User(String loginId, String newLoginPw) {
        // 이 빌더는 사용자 1차비밀번호 수정때만 사용할 용도
        this.loginId = loginId;
        this.loginPw = newLoginPw;
    }

    @Builder(builderClassName = "UserUpdateSolvableCountBuilder", builderMethodName = "UserUpdateSolvableCountBuilder")
    public User(Integer solvableCount) {
        // 이 빌더는 문제풀기가능잔여횟수 수정때만 사용할 용도
        this.solvableCount = solvableCount;
    }


    // 수정(업데이트) 기능
    public void updateLoginPw(String newLoginPw) {  // 패스워드 변경 기능
        this.loginPw = newLoginPw;
    }
    public void updateSolvableCount(Integer solvableCount) {  // 문제풀기가능잔여횟수 수정 기능
        this.solvableCount = solvableCount;
    }
}
