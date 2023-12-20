package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.QUser;
import me.Giyong8504.MemberBoard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor{

    Optional<User> findByEmail(String email); // 소셜 로그인 후 반환되는 값중 email을 확인.

    default boolean exists(String email) {
        return exists(QUser.user.email.eq(email));
    }
}
