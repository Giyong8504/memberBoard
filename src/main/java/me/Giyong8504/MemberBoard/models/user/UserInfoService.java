package me.Giyong8504.MemberBoard.models.user;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Role;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService { // 정보를 조회하는 서비스 기능

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Optional 형태로 예외를 던지면 쉽게 가능하다.
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        // 사용자에게 부여된 권한을 나타내는 SimpleGrantedAuthority 를 사용
        Role role = Objects.requireNonNullElse(user.getRole(), Role.USER);
        List<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role.name()));

        return UserInfo.builder()
                .userNo(user.getUserNo())
                .password(user.getPassword())
                .userNm(user.getUserNm())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .authorities(authorities)
                .build();
    }
}
