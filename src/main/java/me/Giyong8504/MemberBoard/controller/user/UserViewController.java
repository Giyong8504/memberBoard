package me.Giyong8504.MemberBoard.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Utils;
import me.Giyong8504.MemberBoard.controller.JoinForm;
import me.Giyong8504.MemberBoard.dto.FindPwRequest;
import me.Giyong8504.MemberBoard.models.user.UserJoinService;
import me.Giyong8504.MemberBoard.service.FindPwService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@SessionAttributes("EmailAuthVerified")
public class UserViewController {

    private final UserJoinService userJoinService;
    private final FindPwService findPwService;


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


    @GetMapping("/mypage")
    public String myPage() {

        return "user/mypage";
    }

    @GetMapping("/admin/index")
    public String adIndex() {

        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());

        return "redirect:/login";
    }
}
