package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.UserUtil;
import me.Giyong8504.MemberBoard.controller.user.DeleteIdPasswordForm;
import me.Giyong8504.MemberBoard.controller.user.DeleteIdPasswordValidation;
import me.Giyong8504.MemberBoard.entities.DeleteUser;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.DeleteUserRepository;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class DeleteIdPasswordService {

    private final DeleteIdPasswordValidation validation;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final DeleteUserRepository deleteUserRepository;

    public void deleteIdPassword(DeleteIdPasswordForm form, Errors errors) {
        validation.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

        User user = userUtil.getUser();

        //탈퇴 사용자 정보를 DeleteUser에 저장
        DeleteUser deleteUser = new DeleteUser();

        deleteUser.setEmail(user.getEmail());
        deleteUser.setUserNm(user.getUserNm());
        deleteUser.setMobile(user.getMobile());
        deleteUser.setRole(user.getRole());

        deleteUserRepository.saveAndFlush(deleteUser);

        userRepository.delete(user);
    }
}
