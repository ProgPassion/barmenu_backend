package com.barmenu.security.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordDTO {

    @NotBlank(message = "Define current password")
    private String currentPassword;

    @NotBlank(message = "Define new password")
    @Size(min = 8, max = 75, message = "Password show be at least 8 characters long")
    private String newPassword;
}
