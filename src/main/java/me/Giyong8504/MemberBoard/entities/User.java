package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.*;
import me.Giyong8504.MemberBoard.commons.Role;

@Table(name = "board_user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long userNo;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private String userNm;

    @Column(length = 11)
    private String mobile;

    @Enumerated(EnumType.STRING) // 이름 그대로로 저장하기 위해 STRING 사용
    private Role role = Role.USER;

    @Builder
    public User(Long userNo, String email, String password, String userNm, String mobile, Role role) {
        this.userNo = userNo;
        this.email = email;
        this.password = password;
        this.userNm = userNm;
        this.mobile = mobile;
        this.role = role;
    }


    // 사용자 이름 변경
    public User update(String userNm) {
        this.userNm = userNm;

        return this;
    }
}
