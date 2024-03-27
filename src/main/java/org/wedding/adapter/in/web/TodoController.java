package org.wedding.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.wedding.adapter.in.web.dto.todo.CreateTodoRequest;
import org.wedding.adapter.in.web.dto.todo.UpdateTodoRequset;
import org.wedding.application.port.in.TodoUseCase;
import org.wedding.application.port.in.command.todo.CreateTodoCommand;
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
}
