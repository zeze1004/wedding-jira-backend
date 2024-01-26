package project.wedding.user.repository.mybatis.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.wedding.user.domain.User;

@Getter
@NoArgsConstructor
public class UserDto {
    private int id;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String partnerEmail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @Builder
    public UserDto(int id, String email, String password, String name, String nickName, String partnerEmail, LocalDateTime createdDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.partnerEmail = partnerEmail;
        this.createdDate = createdDate;
    }

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
