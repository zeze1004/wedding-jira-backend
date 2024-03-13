package org.wedding.user.repository.mybatis.dto.request;

import java.time.LocalDateTime;

import org.wedding.common.annotation.ValidPassword;
import org.wedding.domain.user.User;
import org.wedding.user.exception.UserError;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpRequest (
    @NotBlank(message = "EMAIL_IS_REQUIRED", payload = UserError.class)
    @Email(message = "EMAIL_FORMAT_NOT_VALID", payload = UserError.class)
    String email,
    @NotBlank(message = "PASSWORD_IS_REQUIRED", payload = UserError.class)
    @ValidPassword(message = "PASSWORD_NOT_VALID", payload = UserError.class)
    String password,
    @NotBlank(message = "NAME_IS_REQUIRED", payload = UserError.class)
    @Size(min = 2, max = 7, message = "NAME_SIZE_NOT_VALID", payload = UserError.class)
    String name,
    @NotBlank(message = "NICKNAME_IS_REQUIRED", payload = UserError.class)
    @Size(min = 2, max = 10, message = "NICKNAME_SIZE_NOT_VALID", payload = UserError.class)
    String nickName,
    @Email(message = "PARTNER_EMAIL_FORMAT_NOT_VALID", payload = UserError.class)
    String partnerEmail
) {

    public SignUpRequest(String email, String password, String name, String nickName) {
        this(email, password, name, nickName, null);
    }

    public static User toEntity(SignUpRequest signUpRequest) {
        return User.builder()
            .email(signUpRequest.email())
            .password(signUpRequest.password())
            .name(signUpRequest.name())
            .nickName(signUpRequest.nickName())
            .partnerEmail(signUpRequest.partnerEmail())
            .createdDate(LocalDateTime.now())
            .build();
    }

    public String toString() {
        return """
            SignUpRequest{
                email='%s',
                password='%s',
                name='%s',
                nickName='%s',
                partnerEmail='%s'
                createdDate='%s'
            }
            """.formatted(email, password, name, nickName, partnerEmail, LocalDateTime.now());
    }
}
