package me.Giyong8504.MemberBoard.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public boolean sendMail(EmailMessage message, String tpl, Map<String, Object> tplData) {
        String text = null;

        /* 이메일 템플릿을 사용하는 경우 EmailMessage의 제목, 내용, 수신인 및 tplData 추가 치환 속성을전달하고
        타임리프로 번역된 텍스트를 반환값으로 처리 */
        if (StringUtils.hasText(tpl)) {
            tplData = Objects.requireNonNullElse(tplData, new HashMap<>());
            Context context = new Context(); //thymeleaf

            tplData.put("to", message.to());
            tplData.put("subject", message.subject());
            tplData.put("message", message.message());

            context.setVariables(tplData);

            text = templateEngine.process("email/" + tpl, context);
        } else { // 템플릿 전송이 아닐 경우 메시지로 대체
            text = message.message();
        }

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false,"UTF-8");
            mimeMessageHelper.setTo(message.to()); // 메일 수신자
            mimeMessageHelper.setSubject(message.subject()); // 메일 제목
            mimeMessageHelper.setText(text, true); // 메일 내용
            javaMailSender.send(mimeMessage); // 메일 전송

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean sendMail(EmailMessage message) {
        return sendMail(message, null, null);
    }
}
