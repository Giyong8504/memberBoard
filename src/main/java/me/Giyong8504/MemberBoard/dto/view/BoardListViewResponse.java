package me.Giyong8504.MemberBoard.dto.view;

import lombok.Getter;
import me.Giyong8504.MemberBoard.entities.BoardData;

@Getter
public class BoardListViewResponse {

    private Long id;
    private String title;
    private String content;
    private String author;

    public BoardListViewResponse(BoardData boardData) {
        this.id = boardData.getId();
        this.title = boardData.getTitle();
        this.content = boardData.getContent();
        this.author = boardData.getAuthor();
    }
}
