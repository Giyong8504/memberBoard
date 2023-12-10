package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.*;
import me.Giyong8504.MemberBoard.commons.Role;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(length = 40, nullable = false, unique = true)
    private String userId;

    @Column(length = 40, nullable = false)
    private String userNm;

    @Column(length = 60, nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String email;

    @Column(length = 11)
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Builder
    public User(Long userNo, String userId, String email, String userPw, String userNm, String mobile, Role role) {
        this.userNo = userNo;
        this.userId = userId;
        this.email = email;
        this.userPw = userPw;
        this.userNm = userNm;
        this.mobile = mobile;
        this.role = role;
    }
}
