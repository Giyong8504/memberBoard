package me.Giyong8504.MemberBoard.dto.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.Giyong8504.MemberBoard.entities.BoardData;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardViewResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime reg_dt;

    public BoardViewResponse(BoardData boardData) {
        this.id = boardData.getId();
        this.title = boardData.getTitle();
        this.content = boardData.getContent();
        this.author = boardData.getAuthor();
        this.reg_dt = boardData.getRegDt();

    }
}
