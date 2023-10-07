package com.barmenu.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name is not defined")
    private String firstname;

    @NotBlank(message = "Surname is not defined")
    private String lastname;

    @NotBlank(message = "Email is not defined")
    @Email(message = "Wrong email format")
    private String email;

    @NotBlank(message = "Define new password")
    @Size(min = 8, max = 75, message = "Password show be at least 8 characters long")
    private String password;
}
