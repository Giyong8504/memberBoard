package me.Giyong8504.MemberBoard.controller.user;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.UserUtil;
import me.Giyong8504.MemberBoard.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class DeleteIdPasswordValidation implements Validator {

    private final UserUtil userUtil;
    private final PasswordEncoder encoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(DeleteIdPasswordForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!userUtil.isLogin()) { // 로그인 상태가 아니라면 체크 X
            return;
        }

        DeleteIdPasswordForm form = (DeleteIdPasswordForm) target;

        String oldPassword = form.getOldPassword();

        // Session에 저장된 비밀번호와 동일한지 체크
        User user = userUtil.getUser();
        String hash = user.getPassword();
        if (StringUtils.hasText(oldPassword) && !encoder.matches(oldPassword, hash)) {
            errors.rejectValue("oldPassword", "mismatch");
        }
    }
}
