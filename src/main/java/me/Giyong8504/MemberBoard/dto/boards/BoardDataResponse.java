package me.Giyong8504.MemberBoard.dto.boards;

import lombok.Getter;
import me.Giyong8504.MemberBoard.entities.BoardData;

@Getter
public class BoardDataResponse {

    private String title;
    private String content;
    private String author;

    public BoardDataResponse(BoardData boardData) {
        this.title = boardData.getTitle();
        this.content = boardData.getContent();
        this.author = boardData.getAuthor();
    }
}
