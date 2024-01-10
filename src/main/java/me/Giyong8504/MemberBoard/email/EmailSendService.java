package me.Giyong8504.MemberBoard.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final JavaMailSender javaMailSender;

    public boolean sendMail(EmailMessage message) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false,"UTF-8");
            mimeMessageHelper.setTo(message.to()); // 메일 수신자
            mimeMessageHelper.setSubject(message.subject()); // 메일 제목
            mimeMessageHelper.setText(message.message(), true); // 메일 내용
            javaMailSender.send(mimeMessage); // 메일 전송

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }
}
