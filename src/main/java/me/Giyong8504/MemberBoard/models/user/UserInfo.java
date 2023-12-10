package me.Giyong8504.MemberBoard.models.user;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import me.Giyong8504.MemberBoard.commons.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class UserInfo implements UserDetails {

    private Long userNo;
    private String userId;
    private String userNm;
    private String userPw;
    private String email;
    private String mobile;
    private Collection<GrantedAuthority> authorities; // 권한에 대한 내용

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 인가에 대한 권한
        return authorities;
    }

    @Override
    public String getPassword() { // 사용자 패스워드 반환
        return userPw;
    }

    @Override
    public String getUsername() { // 사용자 이름 반환.
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
