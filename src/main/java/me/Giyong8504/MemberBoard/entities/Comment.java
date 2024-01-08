package me.Giyong8504.MemberBoard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "board_data_id")
    private BoardData boardData;


    @Builder
    public Comment(String content, String author, BoardData boardData) {
        this.content = content;
        this.author = author;
        this.boardData = boardData;
    }

    public void update(String content) {
        this.content = content;
    }
}
