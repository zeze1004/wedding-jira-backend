package org.wedding.adapter.in.web.dto;

import org.wedding.domain.user.exception.UserError;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyMailRequest(

    @NotBlank(message = "EMAIL_IS_REQUIRED", payload = UserError.class)
    @Email(message = "EMAIL_FORMAT_NOT_VALID", payload = UserError.class)
    String email,
    @Size(min = 6, max = 6, message = "EMAIL_VERIFICATION_CODE_IS_INVALID", payload = UserError.class)
    String verificationCode
) {

    public String toString() {
        return """
                verifyMailRequest{
                    email='%s',
                    verificationCode='%s'
            """.formatted(email, verificationCode);
    }
}
