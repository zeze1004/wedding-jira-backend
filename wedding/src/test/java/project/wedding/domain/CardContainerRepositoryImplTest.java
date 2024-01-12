package project.wedding.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import project.wedding.repository.CardContainerRepositoryImpl;

@SpringBootTest
class CardContainerRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("유저 아이디로 카드 컨테이너를 찾는다.")
    @Test
    void findByUserId() {
        User user = new User("ODEE1");
        CardContainerRepositoryImpl cardContainerRepositoryImpl = new CardContainerRepositoryImpl(jdbcTemplate);
        Assertions.assertThat(cardContainerRepositoryImpl.findByUserId(user.getUserId())).isInstanceOf(CardBoard.class);
    }

    @Test
    void isNotUser() {
    }
}
