package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // email로 사용자 정보를 가져온다.

}
