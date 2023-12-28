package project.wedding.domain;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardTest {
    @DisplayName("카드 인스턴스가 생성될 때마다 카드 id값이 증가하는지 확인해보자")
    @Test
    void createCardInstance() {
        Card card1 = new Card();
        Card card2 = new Card();

        assertThat(card1.getCardId()).isEqualTo(1);
        assertThat(card2.getCardId()).isEqualTo(2);
    }

    @DisplayName("카드에 투두리스트를 만들고 체크상태를 변경되는지 확인")
    @Test
    void addTodoList() {
        Card card = new Card();
        card.addTodo("one", true, "할일1");
        card.addTodo("two", false, "할일2");
        card.addTodo("three", true, "할일3");

        card.changeCheckStatus("two");

        assertThat(card.getTodoList().get(1).get(1)).isEqualTo(true);
        assertThat(card.getTodoList().get(1).get(2)).isEqualTo("할일2");
    }
}
