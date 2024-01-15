package me.Giyong8504.MemberBoard.repositories;

import com.querydsl.core.BooleanBuilder;
import me.Giyong8504.MemberBoard.entities.QUser;
import me.Giyong8504.MemberBoard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor{

    Optional<User> findByEmail(String email); // 소셜 로그인 후 반환되는 값중 email을 확인.

    default boolean exists(String email) { // 이메일이 존재하는지 확인.
        return exists(QUser.user.email.eq(email));
    }

    // 이메일과 회원명으로 조회되는지 체크
    default boolean existsByEmailAndName(String email, String name) {
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.email.eq(email))
                .and(user.userNm.eq(name));

        return exists(builder);
    }
}
