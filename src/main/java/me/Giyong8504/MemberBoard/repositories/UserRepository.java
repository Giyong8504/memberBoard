package me.Giyong8504.MemberBoard.repositories;

import com.querydsl.core.BooleanBuilder;
import me.Giyong8504.MemberBoard.commons.Role;
import me.Giyong8504.MemberBoard.entities.QUser;
import me.Giyong8504.MemberBoard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor{

    Optional<User> findByEmail(String email); // 소셜 로그인 후 반환되는 값중 email을 확인.

    Optional<User> findByUserNo(Long userNo);

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

    // 구글 로그인은 Role 권한이 GOOGLE 이므로 이 값을 활용하여 구글 회원인지 체크
    default boolean oauthUser(String email) {
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.email.eq(email))
                .and(user.role.eq(Role.GOOGLE));
        return exists(builder);
    }
}
