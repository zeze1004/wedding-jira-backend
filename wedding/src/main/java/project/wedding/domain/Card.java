package project.wedding.domain;

import java.util.EnumMap;

import lombok.Getter;

@Getter
public class Card {
    private final int MAX_TODO = 3;  // 한 카드당 투두는 최대 3개까지만 생성 가능
    private final int id;
    private String title;
    private final EnumMap<CheckBox, String> todoMap;
    private final Status status;

    public Card() {
        this.id = CardIdMamager.getLastId();
        this.todoMap = new EnumMap<>(CheckBox.class);
        this.status = Status.BACKLOG;
    }

    public int getCardId() {
        return this.id;
    }

    public EnumMap<CheckBox, String> getTodos(int cardId) {
        return this.todoMap;
    }
}
