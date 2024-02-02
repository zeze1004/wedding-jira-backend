package project.wedding.user.repository.mybatis.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.wedding.user.domain.User;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String partnerEmail;
    private LocalDateTime createdDate;

    public static UserDto of(User user) {
        return UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .name(user.getName())
            .nickName(user.getNickName())
            .partnerEmail(user.getPartnerEmail())
            .createdDate(user.getCreatedDate())
            .build();
    }

    public String toString() {
        return """
            UserDto{
                id='%s',
                email='%s',
                password='%s',
                name='%s',
                nickName='%s',
                partnerEmail='%s',
                createdDate='%s'
            }
            """.formatted(id, email, password, name, nickName, partnerEmail, createdDate);
    }
}
