package me.Giyong8504.MemberBoard.models.user;

import lombok.*;
import me.Giyong8504.MemberBoard.commons.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class UserInfo implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String mobile;
    private Role role;
    private Collection<GrantedAuthority> authorities; // 권한에 대한 내용


    @Override // 인가에 대한 권한
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override // 사용자 id를 반환(고유한 값)
    public String getUsername() {
        return null;
    }

    @Override // 계정 만료 여부 반환
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠금 여부 변환
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 패스워드 만료 여부 반환
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정 사용 가능 여부 확인
    public boolean isEnabled() {
        return true;
    }
}
