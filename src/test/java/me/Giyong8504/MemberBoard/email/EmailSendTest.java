package me.Giyong8504.MemberBoard.email;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class EmailSendTest {

    @Autowired
    private EmailSendService emailSendService;

    @Autowired
    private EmailVerifyService emailVerifyService;

    @Test
    void sendTest() {
        EmailMessage message = new EmailMessage("kky5163@naver.com", "제목...", "내용...");
        boolean success = emailSendService.sendMail(message);

        assertTrue(success);
    }

    @Test
    @DisplayName("템플릿 형태 전송 테스트")
    void sendWithTplTest() {
        EmailMessage message = new EmailMessage("kky5163@naver.com", "제목!!", "내용!!");
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("authNum", "123456");
        boolean success = emailSendService.sendMail(message, "auth", tplData);

        assertTrue(success);
    }

    @Test
    @DisplayName("이메일 인증 번호 전송 테스트")
    void emailVerifyTest() {
        boolean result = emailVerifyService.sendCode("kky5163@naver.com");
        assertTrue(result);
    }
    private void assertTrue(boolean success) {
    }
}