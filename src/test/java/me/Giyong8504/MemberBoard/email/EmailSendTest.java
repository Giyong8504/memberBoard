package me.Giyong8504.MemberBoard.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSendTest {

    @Autowired
    private EmailSendService emailSendService;

    @Test
    void sendTest() {
        EmailMessage message = new EmailMessage("kky5163@naver.com", "제목...", "내용...");
        boolean success = emailSendService.sendMail(message);

        assertTure(success);
    }

    private void assertTure(boolean success) {
    }
}