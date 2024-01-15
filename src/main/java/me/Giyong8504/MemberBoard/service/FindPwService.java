package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Utils;
import me.Giyong8504.MemberBoard.controller.user.FindPwValidation;
import me.Giyong8504.MemberBoard.dto.FindPwRequest;
import me.Giyong8504.MemberBoard.email.service.EmailMessage;
import me.Giyong8504.MemberBoard.email.service.EmailSendService;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FindPwService {

    private final FindPwValidation findPwValidation;
    private final UserRepository userRepository;
    private final EmailSendService emailSendService;
    private final PasswordEncoder passwordEncoder;
    private final Utils utils;

    public void process(FindPwRequest form, Errors errors) {
        findPwValidation.validate(form, errors);

        if (errors.hasErrors()) { // 유효성 검사 실패시 처리 중단
            return;
        }

        // 비밀번호 초기화
        reset(form.email());
    }

    public void reset(String email) {
        /* 비밀번호 초기화 S */
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found" + email));

        String newPassword = utils.randomChars(12); // 초기화 비밀번호는 12자로 생성
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.saveAndFlush(user);

        /* 비밀번호 초기화 E */
        EmailMessage emailMessage = new EmailMessage(email, Utils.getMessage("Email.password.reset", "commons"),
                Utils.getMessage("Email.password.reset", "commons"));
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("password", newPassword);
        emailSendService.sendMail(emailMessage, "passwordReset", tplData);
    }
}
