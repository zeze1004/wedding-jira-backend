package project.wedding.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CardContainerTest {

    @Test
    void save() {
        CardContainer cardContainer = new CardContainer();
        Card card = new Card();

        for (int i = 0; i < 20; i++) {
            cardContainer.save(card);
        }

        // 최대 카드 생성 초과 시 예외 처리
        assertThatThrownBy(() -> cardContainer.save(card))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("생성할 수 있는 카드 수를 초과했습니다.");
    }
}
