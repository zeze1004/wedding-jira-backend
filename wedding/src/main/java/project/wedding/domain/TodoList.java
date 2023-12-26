package project.wedding.domain;

import static project.wedding.domain.TodoList.TodoEnum.*;

import java.util.EnumMap;
import java.util.Map;

public class TodoList {
    private final EnumMap<TodoEnum, String> todoMap= new EnumMap<>(TodoEnum.class);

    public enum CheckStatus {
        UNCHECK, CHECK
    }
    public enum TodoEnum {
        TODO_1(CheckStatus.UNCHECK), TODO_2(CheckStatus.UNCHECK), TODO_3(CheckStatus.UNCHECK);

        private CheckStatus checkStatus;
        TodoEnum(CheckStatus checkState) {
            this.checkStatus = checkState;
        }
    }

    public void writeTodo(TodoEnum todoEnum, String todo) {
        switch (todoEnum) {
            case TODO_1:
                todoMap.put(TODO_1, todo);
                break;
            case TODO_2:
                todoMap.put(TODO_2, todo);
                break;
            case TODO_3:
                todoMap.put(TODO_3, todo);
                break;
        }
    }

    public void changeCheckStatus(TodoEnum todoEnum) {
        switch (todoEnum) {
            case TODO_1:
                TODO_1.checkStatus = TODO_1.checkStatus == CheckStatus.UNCHECK ? CheckStatus.CHECK : CheckStatus.UNCHECK;
                break;
            case TODO_2:
                TODO_2.checkStatus = TODO_2.checkStatus == CheckStatus.UNCHECK ? CheckStatus.CHECK : CheckStatus.UNCHECK;
                break;
            case TODO_3:
                TODO_3.checkStatus = TODO_3.checkStatus == CheckStatus.UNCHECK ? CheckStatus.CHECK : CheckStatus.UNCHECK;
                break;
        }
    }

    public EnumMap<TodoEnum, String> getTodoMap() {
        for (Map.Entry<TodoEnum, String> todosCheckStatusEntry : todoMap.entrySet()) {
            System.out.println(todosCheckStatusEntry.getKey() + " " + todosCheckStatusEntry.getKey().checkStatus + " " + todosCheckStatusEntry.getValue());
        }
        return todoMap;
    }
}
