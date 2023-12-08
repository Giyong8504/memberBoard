package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.*;
import me.Giyong8504.MemberBoard.commons.Role;

@Table(name= "user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false) // 업데이트 시 수정 불가
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(length = 11)
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Builder
    public User(String email, String password, String nickname, String mobile, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.mobile = mobile;
        this.role = role;
    }
}
