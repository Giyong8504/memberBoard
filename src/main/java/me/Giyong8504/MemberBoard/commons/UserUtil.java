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
     * 현재 로그인 중인 회원 정보를 가져옴.
     *
     * @return 현재 로그인 중인 회원 정보 객체 반환
     */
    public UserInfo getMember() {
        UserInfo memberInfo = (UserInfo) session.getAttribute("userInfo");
        return memberInfo;
    }

    /**
     * 현재 로그인한 회원 정보를 엔티티 객체로 변환하여 반환.
     *
     * @return 로그인 중인 회원의 정보의 엔티티 객체를 반환. 로그인중이 아닌경우 null 반환
     */
    public User getUser() {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if (userInfo != null) {
            return userInfo.getUser();
        }

        return null;
    }

}
