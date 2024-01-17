package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()  -> new IllegalArgumentException("Unexpected user"));
    }

    // 현재 로그인한 사용자 정보 가져오기
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return (User) authentication.getPrincipal();
    }
}
