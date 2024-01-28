package me.Giyong8504.MemberBoard.dto.user;

import lombok.Getter;
import me.Giyong8504.MemberBoard.commons.Role;
import me.Giyong8504.MemberBoard.entities.User;

import java.time.LocalDateTime;

@Getter
public class UserListResponse {

    private Long id;
    private String email;
    private String userNm;
    private String mobile;
    private LocalDateTime reg_dt;
    private LocalDateTime mod_dt;
    private Role role;

    public UserListResponse(User user) {
        this.id = user.getUserNo();
        this.email = user.getEmail();
        this.userNm = user.getUserNm();
        this.mobile = user.getMobile();
        this.reg_dt = user.getRegDt();
        this.mod_dt = user.getModDt();
        this.role = user.getRole();
    }
}
