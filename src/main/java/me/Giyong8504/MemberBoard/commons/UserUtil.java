package me.Giyong8504.MemberBoard.commons;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.models.user.UserInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final HttpSession session;

    /**
     * 로그인 여부 체크
     *
     * @return
     */
    public boolean isLogin() {
        return getUser() != null;
    }

    /**
     * 관리자 여부 체크
     *
     * @return
     */
    public boolean isAdmin() {

        return isLogin() && getUser().getRole() == Role.ADMIN;
    }

    /**
     * 로그인한 회원 정보
     *
     * @return
     */
    public User getUser() {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if (userInfo != null) {
            return userInfo.getUser();
        }

        return null;
    }
}
