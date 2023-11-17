package project.wedding.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.Test;

class TodoCardListTest {

	@Test
	void createCardTest() {
		TodoCardList todoCardList = new TodoCardList();
		todoCardList.createCard(new TodoCard());

		assertThat(todoCardList.getTodoCards()).hasSize(1);
	}

	@Test
	void writeCardTest() {
		TodoCardList todoCardList = new TodoCardList();
		// TodoCard todoCard = new TodoCard();
		todoCardList.createCard(new TodoCard());
		todoCardList.writeCard(0, "제목", "투두");
		assertThat(todoCardList.getTodoCards()).hasSize(1);
		assertThat(todoCardList.getTodoCards().get(0).id).isEqualTo(0);
		assertThat(todoCardList.getTodoCards().get(0).title).isEqualTo("제목");
		assertThat(todoCardList.getTodoCards().get(0).todo).isEqualTo("투두");
	}

	@Test
	void removeTest() {
		TodoCardList todoCardList = new TodoCardList();

		todoCardList.createCard(new TodoCard());
		assertThat(todoCardList.getTodoCards()).hasSize(1);

		todoCardList.remove(0);
		assertThat(todoCardList.getTodoCards()).hasSize(0);
	}

	@Test
	void checkCardNum() {
		TodoCardList todoCardList = new TodoCardList();

		for (int i = 0; i < 21; i++) {
			todoCardList.createCard(new TodoCard());
		}

		assertThatThrownBy(() -> todoCardList.checkCardNum())
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("생성할 수 있는 카드 수를 초과했습니다.");
	}
}