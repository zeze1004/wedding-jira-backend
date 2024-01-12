package project.wedding.api.controller.card;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.wedding.api.service.card.CreateCardService;
import project.wedding.domain.User;

@SpringBootTest
class CardApiControllerTest {

    @Autowired
    private CreateCardService createCardService;

    @DisplayName("클라이언트에서 user ID를 주면 서비스를 호출한다.")
    @Test
    void createCardControllerTest() {
        // given
        User user1 = new User("ODEE1");
        CardApiController cardApiController = new CardApiController(createCardService);

        // when
        cardApiController.createCard(user1.getUserId());

        // then
        assertThat(createCardService).isNotNull();
    }

}

