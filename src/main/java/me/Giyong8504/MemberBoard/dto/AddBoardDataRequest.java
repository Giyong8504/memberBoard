package me.Giyong8504.MemberBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.Giyong8504.MemberBoard.entities.BoardData;

@Getter
@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor //모든 필드값을 파라미터로 받는 생성자 추가.
public class AddBoardDataRequest {

    private String title;
    private String content;

    public BoardData toEntity(String author) {
        return BoardData.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
