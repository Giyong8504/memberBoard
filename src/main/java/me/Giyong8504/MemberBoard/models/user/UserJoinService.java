package me.Giyong8504.MemberBoard.models.user;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Role;
import me.Giyong8504.MemberBoard.controller.JoinForm;
import me.Giyong8504.MemberBoard.controller.JoinValidator;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class UserJoinService {

    private final UserRepository userRepository;
    private final JoinValidator joinValidator;
    private final PasswordEncoder passwordEncoder;

    public void join(JoinForm form, Errors errors) {
        joinValidator.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .userNm(form.getUserNm())
                .mobile(form.getMobile())
                .role(Role.USER) // 회원가입시 기본 USER로 일반회원.
                .build();

        userRepository.saveAndFlush(user);
    }
}
