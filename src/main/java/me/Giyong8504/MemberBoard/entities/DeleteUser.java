package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Role;

@Table(name = "delete_user")
@Entity
@NoArgsConstructor
@Data
public class DeleteUser extends BaseEntity {

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
