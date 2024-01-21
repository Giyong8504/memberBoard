package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.UserUtil;
import me.Giyong8504.MemberBoard.controller.user.ChangePasswordForm;
import me.Giyong8504.MemberBoard.controller.user.ChangePasswordValidation;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

    private final UserRepository userRepository;
    private final ChangePasswordValidation changePasswordValidation;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;

    public void changePassword(ChangePasswordForm form, Errors errors) {
        changePasswordValidation.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

        // password 변경 시 암호화 후 저장
        User user = userUtil.getUser();
        user.setPassword(passwordEncoder.encode(form.getNewPassword()));

        userRepository.saveAndFlush(user);
    }
}
