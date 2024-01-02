package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardData extends BaseEntity{ //공통부분을 상속받아 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 증가
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    // cascade로 BoardData에 저장,수정,삭제 될떄 해당 연관 댓글 자동 처리
    @OneToMany(mappedBy = "boardData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public BoardData(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 댓글 추가 메서드
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBoardData(this);
    }

    // 댓글 제거 메서드
    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setBoardData(null);
    }

}
