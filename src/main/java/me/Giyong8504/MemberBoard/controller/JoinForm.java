package me.Giyong8504.MemberBoard.controller;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String userPwRe;

    @NotBlank
    private String userNm;

    private String mobile;

    @AssertTrue
    private boolean agree;
}

