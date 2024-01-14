package me.Giyong8504.MemberBoard.email.controller;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.rests.JSONData;
import me.Giyong8504.MemberBoard.email.service.EmailVerifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailApiController {

    private final EmailVerifyService verifyService;

    // 이메일 인증코드 발급
    @GetMapping("/api/email/verify")
    public JSONData<Object> sendVerifyEmail(@RequestParam("email") String email) {
        JSONData<Object> data = new JSONData<>();

        boolean result = verifyService.sendCode(email);
        data.setSuccess(result);

        return data;
    }

    // 발급받은 인증코드와 사용자 입력 코드의 일치 여부 체크
    @GetMapping("/api/email/auth_check")
    public JSONData<Object> checkVerifiedEmail(@RequestParam("authNum") int authNum) {
        JSONData<Object> data = new JSONData<>();

        boolean result = verifyService.check(authNum);
        data.setSuccess(result);

        return data;
    }
}
