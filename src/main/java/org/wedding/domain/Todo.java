package org.wedding.domain;

import java.util.List;

import org.wedding.application.port.in.TodoListPort;

import lombok.Getter;

@Getter
public class Todo implements TodoListPort {
    private final int todoId;
    private final String todoItem;
    private boolean todoCheckStatus; // todoCheckStatus가 true면 todo 완료, false면 미완료

    public Todo(int todoId, String todoItem) {
        this.todoId = todoId;
        this.todoItem = todoItem;
        this.todoCheckStatus = false;
    }

    // 체크박스 체크 여부 변경
    @Override
    public void changeCheckStatus(String todoId) {
        this.todoCheckStatus = !this.todoCheckStatus;
    }


    @Override
    public void addTodo(Todo todo) {

    }

    @Override
    public void modifyTodoItem(String todoId, String todoItem) {

    }

    @Override
    public List<Todo> getAllTodos() {
        return null;
    }

    public String toString() {
        return """
            Todo{
                todoId='%s',
                todoDescription='%s',
                checkStatus='%s'
            }
            """.formatted(todoId, todoItem, todoCheckStatus);
    }
}
