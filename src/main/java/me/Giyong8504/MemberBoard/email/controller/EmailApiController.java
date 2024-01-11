package me.Giyong8504.MemberBoard.email.controller;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.email.service.EmailVerifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailApiController {

    private final EmailVerifyService verifyService;

    // 이메일 인증 코드 발급
    @GetMapping("/api/email/verify")
    public ResponseEntity<Object> sendVerifyEmail(@RequestParam("email") String email) {
        verifyService.sendCode(email);

        return ResponseEntity.ok().build();
    }

    // 발급받은 인증코드와 사용자 입력 코드의 일치 여부 체크
    @GetMapping("/api/email/auth_check")
    public ResponseEntity<Object> checkVerifiedEmail(@RequestParam("authNum") int authNum) {
        verifyService.check(authNum);

        return ResponseEntity.ok().build();
    }
}
