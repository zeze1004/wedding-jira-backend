package org.wedding.adapter.in.web;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.wedding.adapter.in.web.dto.todo.CreateTodoRequest;
import org.wedding.adapter.in.web.dto.todo.DeleteTodoRequest;
import org.wedding.adapter.in.web.dto.todo.TodoResponse;
import org.wedding.adapter.in.web.dto.todo.UpdateTodoRequset;
import org.wedding.application.port.in.TodoUseCase;
import org.wedding.application.port.in.command.todo.CreateTodoCommand;
import org.wedding.application.port.in.command.todo.DeleteTodoCommand;
import org.wedding.application.port.in.command.todo.ReadTodoCommand;
import org.wedding.application.port.in.command.todo.UpdateTodoCommand;
import org.wedding.common.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name="Todo API", description = "Todo API")
@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoUseCase todoUseCase;

    @PostMapping()
    @Operation(summary = "투두 생성", description = "투두 생성")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> createTodo(@RequestBody @Valid CreateTodoRequest request) {

        CreateTodoCommand command = new CreateTodoCommand(request.cardId(), request.todoItem());

        todoUseCase.createTodo(command);

        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.CREATED, "투두가 생성되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

    @PatchMapping()
    @Operation(summary = "투두 수정", description = "투두 수정")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Void>> updateTodo(@RequestBody @Valid UpdateTodoRequset request) {

        UpdateTodoCommand command = new UpdateTodoCommand(request.cardId(), request.todoId(), request.todoItem(),
            request.todoCheckStatus());

        todoUseCase.updateTodo(command);

        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.OK, "투두가 수정되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping()
    @Operation(summary = "투두 삭제", description = "투두 삭제")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Void>> deleteTodo(@RequestBody @Valid DeleteTodoRequest request) {

        DeleteTodoCommand command = new DeleteTodoCommand(request.cardId(), request.todoId());

        todoUseCase.deleteTodo(command);

        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.OK, "투두가 삭제되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "카드에 속한 모든 투두 조회", description = "카드에 속한 모든 투두 조회")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ArrayList<TodoResponse>> readAllTodoByCardId(@PathVariable int cardId) {

            ArrayList<TodoResponse> response =
                    todoUseCase.getAllTodos(cardId).stream()
                    .map(TodoResponse::fromEntity)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{cardId}/{todoId}")
    @Operation(summary = "카드에 속한 특정 투두 조회" , description = "카드에 속한 특정 투두 조회")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoResponse> readTodoByCardId(@PathVariable int cardId, @PathVariable int todoId) {

        ReadTodoCommand command = new ReadTodoCommand(cardId, todoId);
        TodoResponse response = TodoResponse.fromEntity(todoUseCase.readTodo(command));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
