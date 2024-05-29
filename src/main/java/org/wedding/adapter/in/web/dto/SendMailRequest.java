package org.wedding.adapter.in.web.dto;

import org.wedding.domain.user.exception.UserError;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendMailRequest(

    @NotBlank(message = "EMAIL_IS_REQUIRED", payload = UserError.class) @Email(message = "EMAIL_FORMAT_NOT_VALID", payload = UserError.class) String email) {

    public String toString() {
        return """
                SendMailRequest{
                    email='%s'
            """.formatted(email);
    }
}
