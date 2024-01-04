package me.Giyong8504.MemberBoard.dto.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.Giyong8504.MemberBoard.entities.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentViewResponse {

    private Long id;
    private String content;
    private String author;
    private LocalDateTime reg_dt;
    private Long boardDataId;

    public CommentViewResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.reg_dt = comment.getRegDt();

        if (comment.getBoardData() != null) {
            this.boardDataId = comment.getBoardData().getId();
        }
    }
}
