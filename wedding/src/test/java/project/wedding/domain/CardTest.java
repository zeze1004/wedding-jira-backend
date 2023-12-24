package project.wedding.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardTest {
    @DisplayName("카드 인스턴스가 생성될 때마다 카드 id값이 증가하는지 확인해보자")
    @Test
    void createCardInstance() {
        Card card1 = new Card();
        Card card2 = new Card();

        Assertions.assertThat(card1.getCardId()).isEqualTo(1);
        Assertions.assertThat(card2.getCardId()).isEqualTo(2);
    }
}
