package project.wedding.domain;

import lombok.Getter;

@Getter
public class Card {
    private static final int MAX_TODO = 3;
    private final int id; // cardId
    private String title;
    private final TodoList todoList;
    private final Status status; // cardStatus

    public Card() {
        this.id = CardIdMamager.getLastId();
        this.todoList = new TodoList();
        this.status = Status.BACKLOG;
    }

    public int getCardId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void writeTodo(TodoList.TodoEnum todoEnum, String todo) {
        this.todoList.writeTodo(todoEnum, todo);
    }

    public void changeCheckStatus(TodoList.TodoEnum todoEnum) {
        this.todoList.changeCheckStatus(todoEnum);
    }

    public String toString() {
        return "Card{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", todoMap=" + todoList.getTodoMap() +
            ", status=" + status +
            '}';
    }
}
