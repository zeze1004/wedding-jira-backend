package org.wedding.adapter.in.web.dto;

import org.wedding.common.annotation.ValidPassword;
import org.wedding.domain.user.exception.UserError;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank(message = "EMAIL_IS_REQUIRED", payload = UserError.class)
    @Email(message = "EMAIL_FORMAT_NOT_VALID", payload = UserError.class)
    String email,
    @NotBlank(message = "PASSWORD_IS_REQUIRED", payload = UserError.class)
    @ValidPassword(message = "PASSWORD_NOT_VALID", payload = UserError.class)
    String password
) {

    public String toString() {
        return """
            LoginRequest{
                email='%s',
                password='%s'
            }
            """.formatted(email, password);
    }
}
