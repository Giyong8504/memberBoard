package me.Giyong8504.MemberBoard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.models.user.UserJoinService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserJoinService userJoinService;

    @GetMapping("/join") // 모델에서 사용할 속성 이름을 갑으로 설정
    public String join(@ModelAttribute JoinForm joinForm) {

        return "user/join";
    }

    @PostMapping("/join") // 검증 실패시 상태코드 응답
    public String joinPs(@Valid JoinForm joinForm, Errors errors) {
        userJoinService.join(joinForm, errors);

        if (errors.hasErrors()) {
            return "user/join";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {

        return "user/login";
    }

    @GetMapping("/mypage")
    public String myPage() {

        return "user/mypage";
    }

    @GetMapping("/admin/index")
    public String adIndex() {

        return "admin/index";
    }
}
