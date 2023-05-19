package com.umsinsa.solvingproblemspringproject.domain;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DefaultProblemEntity {

    @Column(name = "recommend_count", columnDefinition = "INT default 0")
    protected Integer recommendCount;


    @PrePersist  // 해당 엔티티를 저장하기 이전에 실행된다.
    public void onPrePersist() {
        this.recommendCount = this.recommendCount == null ? 0 : this.recommendCount;
    }
}