package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Role;
import me.Giyong8504.MemberBoard.commons.UserUtil;
import me.Giyong8504.MemberBoard.controller.user.DeleteIdPasswordForm;
import me.Giyong8504.MemberBoard.controller.user.DeleteIdPasswordValidation;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class DeleteIdPasswordService {

    private final DeleteIdPasswordValidation validation;
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    public void deleteIdPassword(DeleteIdPasswordForm form, Errors errors) {
        validation.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

        // 탈퇴시 회원 비활성화로 권한 변경, 탈퇴 정보 저장
        User user = userUtil.getUser();
        user.setRole(Role.DISABLE);
        user.setDeleteId("Y");
        user.setDeleteEmail(user.getEmail());
        user.setEmail("");
        user.setPassword("");
        userRepository.saveAndFlush(user);
    }
}
