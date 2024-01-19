package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
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

    public void changePassword(ChangePasswordForm form, Errors errors) {
        changePasswordValidation.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }
        User user = User.builder()
                .password(passwordEncoder.encode(form.getNewPassword()))
                .build();

        userRepository.saveAndFlush(user);
    }
}
