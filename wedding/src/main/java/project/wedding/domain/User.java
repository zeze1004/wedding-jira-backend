package project.wedding.domain;

import java.util.logging.Logger;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class User {
    private final String userId;
    // todo: 기 가입된 연인이 있다면 id 를 저장하여, 연인의 cardBoard 를 참조할 수 있도록 한다.
    // private final String loverId;
    private final Logger logger = Logger.getLogger(User.class.getName());

    public User(String userId) {
        this.userId = userId;
        logger.info(userId + " 유저 생성");
    }
}
