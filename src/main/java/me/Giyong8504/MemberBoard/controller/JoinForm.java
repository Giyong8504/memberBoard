package me.Giyong8504.MemberBoard.controller;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinForm {

    @NotBlank
    @Size(min = 6)
    private String userId;

    @NotBlank
    @Size(min = 8)
    private String userPw;

    @NotBlank
    private String userPwRe;

    @NotBlank
    private String userNm;

    @Email
    @NotBlank
    private String email;

    private String mobile;

    @AssertTrue
    private boolean agree;
}

