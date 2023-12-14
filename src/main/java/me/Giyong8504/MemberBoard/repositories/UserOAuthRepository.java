package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.UserOAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOAuthRepository extends JpaRepository<UserOAuth, Long> {
    Optional<UserOAuth> findByEmail(String email); // 소셜 로그인 후 반환되는 값중 email을 확인.
}
