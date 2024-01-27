package project.wedding.user.repository.mybatis.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import project.wedding.common.annotation.ValidPassword;
import project.wedding.user.domain.User;
import project.wedding.user.exception.UserError;

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
            SignUpRequest{리
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
