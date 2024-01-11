package me.Giyong8504.MemberBoard.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository userRepository;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());

        return "redirect:/login";
    }

    // 이메일 중복 여부 체크
    @GetMapping("/api/user/email_dup_check")
    public ResponseEntity<Object> duplicateEmailCheck(@RequestParam("email") String email) {
        boolean emailExists = userRepository.exists(email);

        if (emailExists) {
            // 중복된 이메일이 존재할 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate email");
        } else {
            // 중복된 이메일이 존재하지 않을 경우
            return ResponseEntity.ok().build();
        }
    }
}
