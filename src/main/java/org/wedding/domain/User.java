package org.wedding.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String partnerEmail;
    private LocalDateTime createdDate;

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
