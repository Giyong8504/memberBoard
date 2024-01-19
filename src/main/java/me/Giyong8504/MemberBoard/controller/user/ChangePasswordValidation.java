package me.Giyong8504.MemberBoard.controller.user;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ChangePasswordValidation implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ChangePasswordForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangePasswordForm form = (ChangePasswordForm) target;

        String oldPassword = form.getOldPassword();
        String newPassword = form.getNewPassword();
        String passwordRe = form.getPasswordRe();

        // 기존비밀 번호와 일치하는지 확인
        if (!userRepository.existsByPassword(oldPassword)
                && oldPassword == null && oldPassword.isBlank()) {
            errors.rejectValue("oldPassword", "mismatch");
        }

        // 새로운 비밀번호와 비밀번호 확인이 일치하는지 체크
        if (!newPassword.equals(passwordRe) && newPassword != null && !newPassword.isBlank()
                && passwordRe != null && !passwordRe.isBlank()) {
            errors.rejectValue("passwordRe", "mismatch");
        }
    }
}
