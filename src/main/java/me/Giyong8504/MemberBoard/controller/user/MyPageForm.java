package me.Giyong8504.MemberBoard.controller.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MyPageForm {

    private String email;

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min=8)
    private String newPassword;

    @NotBlank
    @Size(min=8)
    private String passwordRe;
}
