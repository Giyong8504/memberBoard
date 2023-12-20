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
//        session.removeAttribute("requiredEmail");
//        session.removeAttribute("requiredPassword");
//        session.removeAttribute("globalError");
//        session.removeAttribute("email");
//
//        String email = request.getParameter("email");
//        String userPw = request.getParameter("password");
//
//        session.setAttribute("email", email);
//
//        if (email == null || email.isBlank()) { // id가 null이거나 빈값일 때
//            session.setAttribute("requiredEmail", bundle.getString("NotBlank.email"));
//        }
//
//        if (userPw == null || userPw.isBlank()) { // pw가 null이거나 빈값일 때
//            session.setAttribute("requiredPassword", bundle.getString("NotBlank.password"));
//        }
//
//        if (email != null && !email.isBlank() && userPw != null & !userPw.isBlank()) {
//            session.setAttribute("globalError", bundle.getString("login.fail"));
//        }
//
//        // 실패시 로그인 페이지로 다시 돌아간다.
//        response.sendRedirect(request.getContextPath() + "/login");
//    }
//}
