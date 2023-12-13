package me.Giyong8504.MemberBoard.models.user;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService { // 정보를 조회하는 서비스 기능

    private final UserRepository userRepository;

    @Override 
    public UserInfo loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userName);
        if (user == null) { // 값이 null 이면 예외 발생.
            throw new UsernameNotFoundException(userName);
        }

        // 사용자에게 부여된 권한을 나타내는 SimpleGrantedAuthority 를 사용
        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(user.getRole().name())
        );

        return UserInfo.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .userNm(user.getUserNm())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .authorities(authorities)
                .build();
    }
}
