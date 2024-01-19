package me.Giyong8504.MemberBoard.dto;

import lombok.Getter;
import me.Giyong8504.MemberBoard.entities.User;

@Getter
public class ChangePasswordViewResponse {

    private Long userNo;

    public ChangePasswordViewResponse(User user) {
        this.userNo = getUserNo();
    }
}
