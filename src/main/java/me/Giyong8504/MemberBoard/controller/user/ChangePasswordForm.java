package me.Giyong8504.MemberBoard.controller.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordForm {

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min=8)
    private String newPassword;

    @NotBlank
    private String passwordRe;
}
