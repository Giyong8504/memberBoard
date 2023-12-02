package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Setter @Getter
@MappedSuperclass
@EntityListeners(AutoCloseable.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false) //생성시에만 날짜 생성
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column(nullable = false, insertable = false) // 수정 될 때만 날짜 변경
    private LocalDateTime modDt;
}
