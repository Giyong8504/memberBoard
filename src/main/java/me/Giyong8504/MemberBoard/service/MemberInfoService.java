package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.models.user.UserInfo;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService { // 정보를 조회하는 서비스 기능

    private final UserRepository userRepository;

    @Override 
    public UserInfo loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userName);
        if (user == null) { // 값이 null 이면 예외 발생.
            throw new UsernameNotFoundException(userName);
        }
        return UserInfo.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .userNm(user.getUserNm())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .build();
    }
}
