//package me.Giyong8504.MemberBoard.models.user;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import java.io.IOException;
//import java.util.ResourceBundle;
//
//public class LoginFailureHandler implements AuthenticationFailureHandler { // 로그인 실패 시 동작제어
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        HttpSession session = request.getSession();
//
//        // 로그인 실패시 나오는 메세지를 messages 경로에 validations.properties 파일에 작성한 내용을 노출한다.
//        ResourceBundle bundle = ResourceBundle.getBundle("messages.validations");
//
//        // 기존 세션의 기록을 제거한다.
//        session.removeAttribute("requiredUserId");
//        session.removeAttribute("requiredUserPw");
//        session.removeAttribute("globalError");
//        session.removeAttribute("userId");
//
//        String userId = request.getParameter("userId");
//        String userPw = request.getParameter("userPw");
//
//        session.setAttribute("userId", userId);
//
//        if (userId == null || userId.isBlank()) { // id가 null이거나 빈값일 때
//            session.setAttribute("requiredUserId", bundle.getString("NotBlank.userId"));
//        }
//
//        if (userPw == null || userPw.isBlank()) { // pw가 null이거나 빈값일 때
//            session.setAttribute("requiredUserPw", bundle.getString("NotBlank.userPw"));
//        }
//
//        if (userId != null && !userId.isBlank() && userPw != null & !userPw.isBlank()) {
//            session.setAttribute("globalError", bundle.getString("login.fail"));
//        }
//
//        // 실패시 로그인 페이지로 다시 돌아간다.
//        response.sendRedirect(request.getContextPath() + "/login");
//    }
//}
