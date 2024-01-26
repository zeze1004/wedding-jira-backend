package project.wedding.user.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String partnerEmail;
    private LocalDateTime createdDate;

    @Builder
    public User(int id, String email, String password, String name, String nickName, String partnerEmail, LocalDateTime createdDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.partnerEmail = partnerEmail;
        this.createdDate = createdDate;
    }

    public User(String name) {
        this.name = name;
    }

    public String toString() {
        return """
            User{
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
