package project.wedding.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class TodoCardList {
	// private TodoCard todoCard;
	private final List<TodoCard> todoCards = new ArrayList<>();

	public void createCard(TodoCard todoCard) {
		if (checkCardNum()) {
			todoCards.add(todoCard);
		}
	}

	public void writeCard(int id, String title, String todo) {
		todoCards.get(id).setTitle(title);
		todoCards.get(id).setTodo(todo);
	}

	public void remove(int id) {
		todoCards.remove(todoCards.get(id));
	}

	public boolean checkCardNum() {
		if (todoCards.size() >= 21) {
			throw new IllegalArgumentException("생성할 수 있는 카드 수를 초과했습니다.");
		}
		return true;
	}

	// public moveStatus(TodoCard todoCard)
}
