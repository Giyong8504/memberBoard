package me.Giyong8504.MemberBoard.controller.user;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.FindPwRequest;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FindPwValidation implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(FindPwRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        //이메일과 회원명 조합으로 조회되는지 체크
        FindPwRequest form = (FindPwRequest) target;
        String email = form.email();
        String name = form.name();

        // 문자열 유효성 검증
        if (StringUtils.hasText(email) && StringUtils.hasText(name)
                && !userRepository.existsByEmailAndName(email, name)) {
            errors.reject("NotFound.user");
        }

        // 이메일과 비밀번호가 null 값인 조합으로 조회되는지 체크
        if (userRepository.isPasswordNull(email)) {
            errors.reject("Google.user");
        }
    }
}
