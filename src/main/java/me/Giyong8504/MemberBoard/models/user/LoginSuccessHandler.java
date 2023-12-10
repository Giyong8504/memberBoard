package me.Giyong8504.MemberBoard.models.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler { // 로그인 성공 동작 제어
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        // 기존 세션의 기록을 제거한다.
        session.removeAttribute("requiredUserId");
        session.removeAttribute("requiredUserPw");
        session.removeAttribute("globalError");
        session.removeAttribute("userId");

        // 회원 정보를 세션에 기록
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        session.setAttribute("userInfo", userInfo);

        // redirect 값이 있으면 /board 페이지로 이동.
        response.sendRedirect(request.getContextPath() + "/board");
    }
}
