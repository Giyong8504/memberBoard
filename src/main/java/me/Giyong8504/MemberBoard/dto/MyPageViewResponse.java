package me.Giyong8504.MemberBoard.dto;

import lombok.Getter;
import me.Giyong8504.MemberBoard.entities.User;

@Getter
public class MyPageViewResponse {

    private String email;
    private String password;
    private String userNm;
    private String mobile;

    public MyPageViewResponse(User user) {
        this.email = getEmail();
        this.password = getPassword();
        this.userNm = getUserNm();
        this.mobile = getMobile();
    }
}
