package me.Giyong8504.MemberBoard.controller.user;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.rests.JSONData;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository userRepository;

    // 이메일 중복 여부 체크
    @GetMapping("/api/user/email_dup_check")
    public JSONData<Object> duplicateEmailCheck(@RequestParam("email") String email) {
        boolean isExists = userRepository.exists(email);

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(isExists);

        return data;
    }
}
