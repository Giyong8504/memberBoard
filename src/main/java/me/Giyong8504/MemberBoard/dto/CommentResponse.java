package me.Giyong8504.MemberBoard.dto;

import lombok.Builder;
import lombok.Getter;
import me.Giyong8504.MemberBoard.entities.Comment;

@Getter
public class CommentResponse {

    private String content;
    private String author;

    public CommentResponse(Comment comment) {
        this.content = comment.getContent();
        this.author = comment.getAuthor();
    }
}
