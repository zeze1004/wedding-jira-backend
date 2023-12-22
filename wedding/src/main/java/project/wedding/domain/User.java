package project.wedding.domain;

import lombok.Getter;

@Getter
public class User {
    private String userId;

    public User(String userId) {
        this.userId = userId;
        System.out.println(userId + "가 생성됐습니다.");
    }
}
