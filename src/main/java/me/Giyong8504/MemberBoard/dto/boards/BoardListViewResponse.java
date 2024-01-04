package me.Giyong8504.MemberBoard.dto.boards;

import lombok.Getter;
import me.Giyong8504.MemberBoard.entities.BoardData;

import java.time.LocalDateTime;

@Getter
public class BoardListViewResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime reg_dt;

    public BoardListViewResponse(BoardData boardData) {
        this.id = boardData.getId();
        this.title = boardData.getTitle();
        this.content = boardData.getContent();
        this.author = boardData.getAuthor();
        this.reg_dt = boardData.getRegDt();
    }
}
