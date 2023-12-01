package project.wedding.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.wedding.controller.CardContainer;
import project.wedding.controller.KanbanBoard;
import project.wedding.usecase.CardUseCase;

class KanbanBoardTest {
    @DisplayName("칸반보드에 의해 생성된 유저의 카드가 카드컨테이너에 잘 담겨졌는지 확인해보자")
    @Test
    void attachCard() {
        CardUseCase cardUseCase = new CardUseCase(new Card(Status.BACKLOG, null, null, null, 0));

        User odee = new User("odee");
        CardContainer cardContainer = new CardContainer();
        KanbanBoard kanbanBoard = new KanbanBoard(odee, cardContainer, cardUseCase);

        kanbanBoard.createCard(cardContainer);

        assertThat(cardContainer.toString()).isEqualTo("cards.size = " + 1);
    }

    @DisplayName("칸반보드에 의해 생성된 유저의 카드가 카드컨테이너에 잘 담겨졌는지 확인해보자")
    @Test
    void deleteCard() {
        CardUseCase cardUseCase = new CardUseCase(new Card(Status.BACKLOG, null, null, null, 0));

        User odee = new User("odee");
        CardContainer cardContainer = new CardContainer();
        KanbanBoard kanbanBoard = new KanbanBoard(odee, cardContainer, cardUseCase);

        // 카드 2개 만들 때
        kanbanBoard.createCard(cardContainer);
        kanbanBoard.createCard(cardContainer);

        // 하나 삭제
        kanbanBoard.deleteCard(cardContainer, 1);

        // 카드 사이즈는 1
        assertThat(cardContainer.toString()).isEqualTo("cards.size = " + 1);

    }

    @DisplayName("칸반보드가 필요한 카드를 카드컨테이너에게 리턴 받을 수 있는지 확인해보자")
    @Test
    void readCard() {
        CardUseCase cardUseCase = new CardUseCase(new Card(Status.BACKLOG, null, null, null, 0));

        User odee = new User("odee");
        CardContainer cardContainer = new CardContainer();
        KanbanBoard kanbanBoard = new KanbanBoard(odee, cardContainer, cardUseCase);

        // 카드 2개 만들 때
        kanbanBoard.createCard(cardContainer);
        kanbanBoard.createCard(cardContainer);

        Card oneCard = kanbanBoard.readCard(cardContainer, 1);

        // 어떻게 비교할지 구현 필요
    }

    @DisplayName("칸반보드가 카드컨테이너의 카드에서 타이틀과 투두를 수정할 수 있는지 확인해보자")
    @Test
    void updateTodo() {
        CardUseCase cardUseCase = new CardUseCase(new Card(Status.BACKLOG, null, null, null, 0));

        User odee = new User("odee");
        CardContainer cardContainer = new CardContainer();
        KanbanBoard kanbanBoard = new KanbanBoard(odee, cardContainer, cardUseCase);

        // 카드 2개 만들 때
        kanbanBoard.createCard(cardContainer);
        kanbanBoard.createCard(cardContainer);

        kanbanBoard.updateTodo(cardContainer, 1, "수정하기", "내용은 이러하다");
    }
}
