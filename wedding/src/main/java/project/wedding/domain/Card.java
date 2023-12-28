package project.wedding.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Card {
    private final int cardId;
    private String title;
    private final List<List<Object>> todoList;
    private final CardStatus cardStatus;

    public Card() {
        this.cardId = CardIdMamager.getLastId();
        this.todoList = new ArrayList<>();
        this.cardStatus = CardStatus.BACKLOG;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addTodo(String todoId, boolean checkStatus, String todo) {
        List<Object> todoArray = new ArrayList<>();
        todoArray.add(todoId);
        todoArray.add(checkStatus);
        todoArray.add(todo);
        this.todoList.add(todoArray);
    }

    public List<Object> getTodo(String todoId) {
        return findTodoList(todoId);
    }


    public void changeCheckStatus(String todoId) {
        List<Object> todoArray = findTodoList(todoId);
        boolean checkStatus = (boolean) todoArray.get(1);
        todoArray.set(1, !checkStatus);
    }

    public void changeTodo(String todoId, String todo) {
        List<Object> todoArray = findTodoList(todoId);
        todoArray.set(2, todo);
    }

    public List<Object> findTodoList(String todoId) {
        for (List<Object> todoArray : todoList) {
            if (todoArray.get(0).equals(todoId)) {
                return todoArray;
            }
        }
        throw new IllegalArgumentException("해당하는 todo가 없습니다.");
    }

    public String toString() {
        return """
            Card{
                CardId=%d,
                title='%s',
                todoList=%s,
                status=%s
            }
            """.formatted(cardId, title, todoList, cardStatus);
    }
}
