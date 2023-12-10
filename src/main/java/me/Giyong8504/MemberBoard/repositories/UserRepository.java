package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String id); // id로 사용자 정보를 가져온다.

}
