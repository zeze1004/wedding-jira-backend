package project.wedding.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardBoardTest {

    @DisplayName("CardBoard에서 Card 객체를 만들 때 자료구조에 카드id와 카드객체가 들어가는지 확인해본다")
    @Test
    void createCard() {
        CardBoard cardBoard = new CardBoard();
        cardBoard.createCard();
        cardBoard.createCard();

        String cardListOutput =
                "[[1: [[null, null], [null, null], [null, null]]], " +
                "[2: [[null, null], [null, null], [null, null]]]]";


        Assertions.assertThat(cardBoard.getAllValues()).isEqualTo(cardListOutput);


    }
}
