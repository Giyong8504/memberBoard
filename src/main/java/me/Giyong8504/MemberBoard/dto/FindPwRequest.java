package me.Giyong8504.MemberBoard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FindPwRequest(
        @NotBlank @Email
        String email,

        @NotBlank
        String name
) {}
