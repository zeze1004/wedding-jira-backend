package org.wedding.domain.todo.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.wedding.common.exception.CommonError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoError implements CommonError {

    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 TODO가 존재하지 않습니다."),
    TODO_ID_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST, "TODO ID는 0보다 커야합니다."),
    TODO_ITEM_IS_REQUIRED(HttpStatus.BAD_REQUEST, "TODO 내용을 입력해주세요."),
    TODO_ITEM_SIZE_NOT_VALID(HttpStatus.BAD_REQUEST, "TODO 내용은 2자 이상 30자 이하 입력해주세요."),
    TODO_CHECK_STATUS_EMPTY(HttpStatus.BAD_REQUEST, "TODO 체크 상태가 비어있습니다."),
    TODO_NOT_CREATED(HttpStatus.INTERNAL_SERVER_ERROR, "TODO 생성에 실패했습니다."),
    TODO_NOT_UPDATED(HttpStatus.INTERNAL_SERVER_ERROR, "TODO 수정에 실패했습니다."),
    TODO_NOT_DELETED(HttpStatus.INTERNAL_SERVER_ERROR, "TODO 삭제에 실패했습니다."),
    TODO_NOT_MOVED_STATUS(HttpStatus.INTERNAL_SERVER_ERROR, "TODO 상태 변경에 실패했습니다."),
    TODO_MAX_NUMBER_EXCEEDED(HttpStatus.UNPROCESSABLE_ENTITY, "최대 TODO 개수를 초과했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public static TodoError of(String error) {
        return Arrays.stream(TodoError.values())
            .filter(todoError -> todoError.name().equalsIgnoreCase(error))
            .findAny()
            .orElse(null);
    }
}

