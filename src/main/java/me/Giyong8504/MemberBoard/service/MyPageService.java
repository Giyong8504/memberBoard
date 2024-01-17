package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.controller.user.MyPageForm;
import me.Giyong8504.MemberBoard.controller.user.MyPageValidation;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final MyPageValidation myPageValidation;
    private final PasswordEncoder passwordEncoder;

    public void myPage(MyPageForm form, Errors errors) {
        myPageValidation.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }
        User user = User.builder()
                .password(passwordEncoder.encode(form.getNewPassword()))
                .build();

        userRepository.saveAndFlush(user);
    }
}
