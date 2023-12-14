package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Role;

@Table(name = "user_oauth")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserOAuth extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public UserOAuth(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public UserOAuth update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
}
