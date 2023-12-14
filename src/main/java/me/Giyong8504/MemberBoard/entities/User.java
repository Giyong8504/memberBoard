package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.*;
import me.Giyong8504.MemberBoard.commons.Role;

@Table(name = "board_user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(length = 40, unique = true)
    private String userId;

    @Column(length = 40, unique = true)
    private String userNm;

    @Column(length = 60)
    private String userPw;

    @Column(unique = true)
    private String email;

    @Column(length = 11)
    private String mobile;

    @Enumerated(EnumType.STRING) // 이름 그대로로 저장하기 위해 STRING 사용
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

    public User update(String userNm) {
        this.userNm = userNm;

        return this;
    }
}
