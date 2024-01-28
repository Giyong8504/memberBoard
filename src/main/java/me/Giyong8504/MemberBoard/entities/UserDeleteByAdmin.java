package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Role;

@Entity
@Table(name = "user_delete_by_admin")
@NoArgsConstructor
@Data
public class UserDeleteByAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String Email;

    @Column
    private String userNm;

    @Column
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

}
