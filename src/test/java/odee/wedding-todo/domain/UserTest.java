package project.wedding.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.wedding.user.domain.User;

class UserTest {
    @DisplayName("신규 유저가 생성될 때 별개의 cardBoard가 생성되는지 확인해본다")
    @Test
    void createUser() {
        User user1 = new User("ODEE1");
        User user2 = new User("ODEE2");
    }

}
