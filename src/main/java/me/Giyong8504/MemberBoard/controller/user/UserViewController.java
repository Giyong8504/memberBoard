package me.Giyong8504.MemberBoard.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.UserUtil;
import me.Giyong8504.MemberBoard.controller.JoinForm;
import me.Giyong8504.MemberBoard.dto.FindPwRequest;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.models.user.UserJoinService;
import me.Giyong8504.MemberBoard.service.FindPwService;
import me.Giyong8504.MemberBoard.service.ChangePasswordService;
import me.Giyong8504.MemberBoard.service.UserService;
import me.Giyong8504.MemberBoard.service.DeleteIdPasswordService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.*;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@SessionAttributes("EmailAuthVerified")
public class UserViewController {

    private final UserJoinService userJoinService;
    private final FindPwService findPwService;
    private final ChangePasswordService changePasswordService;
    private final UserService userService;
    private final DeleteIdPasswordService deleteIdPasswordService;


    @GetMapping("/join") // 모델에서 사용할 속성 이름을 값으로 설정
    public String join(@ModelAttribute JoinForm joinForm, Model model) {

        // 이메일 인증여부 false로 초기화
        model.addAttribute("EmailAuthVerified", false);

        return "user/join";
    }

    @PostMapping("/join") // 검증 실패시 상태코드 응답
    public String joinPs(@Valid JoinForm joinForm, Errors errors, SessionStatus sessionStatus) {
        userJoinService.join(joinForm, errors);

        if (errors.hasErrors()) {
            return "user/join";
        }

        // EmailAuthVerified 세션값 비우기
        sessionStatus.setComplete();

        return "redirect:/login";
    }

    // 로그인
    @GetMapping("/login")
    public String login() {

        return "user/login";
    }

    @GetMapping("/findId")
    public String findId() {

        return "user/findId";
    }

    // 비밀번호 찾기 양식
    @GetMapping("/findPw")
    public String findPw(@ModelAttribute FindPwRequest form) {

        return "user/findPw";
    }

    // 비밀번호 찾기 처리
    @PostMapping("/findPw")
    public String findPwPs(@Valid FindPwRequest form, Errors errors) {
        findPwService.process(form, errors); // 비밀번호 찾기 처리

        if (errors.hasErrors()) {
            return "user/findPw";
        }

        // 비밀번호 찾기에 이상이 없으면 완료 페이지로 이동
        return "redirect:/findPwDone";
    }

    // 비밀번호 찾기 완료 페이지
    @GetMapping("/findPwDone")
    public String findPwDone() {

        return "user/findPwDone";
    }


    // 마이페이지
    @GetMapping("/myPage")
    public String myPage(Model model /*, Principal principal */) {

        // Spring Security의 Authentication을 얻는다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Spring Security의 방식으로 일반 회원가입 사용자 정보를 가져온다.
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            String userEmail = authentication.getName();
            User currentUser = userService.findByEmail(userEmail);

            model.addAttribute("currentUser", currentUser);
        }

        // OAuth2로 로그인한 경우, OAuth2User 정보 얻는다.
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

            // 필요한 사용자 정보를 추출
            String userEmail = oauthUser.getAttribute("email");

            // email을 사용하여 사용자 정보를 가져오는 메서드
            User currentUser = userService.findByEmail(userEmail);

            /**
             // Principal을 사용할 때 - 매개변수로 Principal을 가져온 상태여야 함.
            String userEmail = principal.getName(); // 현재 로그인한 사용자의 이메일을 구한다.
            User currentUser = userService.findByEmail(userEmail); // 가져온 이메일을 사용해 정보를 구한다.

            // Session을 사용할 때는 userUtil.getUser() 로 가져온다.

             */

            model.addAttribute("currentUser", currentUser);
        }

        return "user/myPage";
    }

    // 비밀번호 변경 페이지
    @GetMapping("/changePassword")
    public String changePassword(@ModelAttribute ChangePasswordForm form, Authentication authentication) {

        // OAuth2 사용자인 경우 myPage로 리다이렉트
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {

            return "redirect:/myPage";
        }

        return "user/changePassword";
    }

    // 비밀번호 유효성 검사
    @PostMapping("/changePassword")
    public String changePasswordPs(@Valid ChangePasswordForm form, Errors errors) {
        changePasswordService.changePassword(form, errors);

        if (errors.hasErrors()) {
            return "user/changePassword";
        }

        return "redirect:/myPage";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());

        return "redirect:/login";
    }

    // 회원탈퇴
    @GetMapping("/deleteId")
    public String deleteId(@ModelAttribute DeleteIdPasswordForm form, Authentication authentication) {

        // OAuth2 사용자인 경우 myPage로 리다이렉트
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {

            return "redirect:/myPage";
        }

        return "user/deleteId";
    }

    // 회원 탈퇴 유효성 검증 후 실행
    @PostMapping("/deleteId")
    public String deleteIdPs(@Valid DeleteIdPasswordForm form, Errors errors) {
        deleteIdPasswordService.deleteIdPassword(form, errors);

        if (errors.hasErrors()) {
            return "user/deleteId";
        }

        return "redirect:/login";
    }
}
