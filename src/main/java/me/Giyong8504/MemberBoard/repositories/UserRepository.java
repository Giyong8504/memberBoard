package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.QUser;
import me.Giyong8504.MemberBoard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    User findByUserId(String id); // id로 사용자 정보를 가져온다.

    Optional<User> findByEmail(String email); // 소셜 로그인 후 반환되는 값중 email을 확인.

    default boolean exists(String userId) {
        QUser user = QUser.user;

        return exists(user.userId.eq(userId));
    }
}
