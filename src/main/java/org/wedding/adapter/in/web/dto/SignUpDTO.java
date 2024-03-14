package org.wedding.adapter.in.web.dto;

import java.time.LocalDateTime;

import org.wedding.common.annotation.ValidPassword;
import org.wedding.domain.user.User;
import org.wedding.domain.user.exception.UserError;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpDTO(
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

    public SignUpDTO(String email, String password, String name, String nickName) {
        this(email, password, name, nickName, null);
    }

    public static User toEntity(SignUpDTO signUpDTO) {
        return User.builder()
            .email(signUpDTO.email())
            .password(signUpDTO.password())
            .name(signUpDTO.name())
            .nickName(signUpDTO.nickName())
            .partnerEmail(signUpDTO.partnerEmail())
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
