package me.Giyong8504.MemberBoard.controller.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteIdPasswordForm {

    @NotBlank
    private String oldPassword;

}
