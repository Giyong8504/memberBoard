package me.Giyong8504.MemberBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.entities.Comment;

@Getter
@NoArgsConstructor // 기본생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
public class AddCommentRequest {

    private String content;
    private String author;

    public Comment toEntity(BoardData boardData, String author) {
        return Comment.builder()
                .content(content)
                .author(author)
                .build();
    }
}
