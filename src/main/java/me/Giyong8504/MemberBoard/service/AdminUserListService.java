package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.UserUtil;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.entities.UserDeleteByAdmin;
import me.Giyong8504.MemberBoard.repositories.UserDeleteByAdminRepository;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserListService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final UserDeleteByAdminRepository userDeleteByAdminRepository;

    // id 값으로 삭제한다.
    public void userDelete(Long id) {
        User user = userRepository.findById(id).orElse(null);

        // ADMIN 일때만 삭제 가능
        if (userUtil.isAdmin()) {

            //탈퇴 사용자 정보를 UserDeleteByAdmin에 저장
            UserDeleteByAdmin userDeleteByAdmin = new UserDeleteByAdmin();

            userDeleteByAdmin.setEmail(user.getEmail());
            userDeleteByAdmin.setUserNm(user.getUserNm());
            userDeleteByAdmin.setMobile(user.getMobile());
            userDeleteByAdmin.setRole(user.getRole());

            userDeleteByAdminRepository.saveAndFlush(userDeleteByAdmin);

            userRepository.delete(user);

        } else {
            throw new IllegalArgumentException("Unauthorized delete");
        }
    }
}
