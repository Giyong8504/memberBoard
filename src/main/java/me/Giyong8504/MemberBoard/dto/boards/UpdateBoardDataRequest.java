package me.Giyong8504.MemberBoard.dto.boards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardDataRequest {

    private String title;
    private String content;
}
