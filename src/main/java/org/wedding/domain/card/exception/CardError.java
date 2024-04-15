package org.wedding.domain.card.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.wedding.common.exception.CommonError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardError implements CommonError {

    CARD_ID_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST, "카드 ID를 전달해주세요."),
    CARD_TITLE_IS_REQUIRED(HttpStatus.BAD_REQUEST, "카드 제목에 할 일을 입력해주세요."),
    CARD_TITLE_SIZE_NOT_VALID(HttpStatus.BAD_REQUEST, "카드 제목은 1자 이상 20자 이하로 입력해주세요."),
    CARD_TITLE_IS_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 존재하는 카드 제목입니다."),
    CARD_DEADLINE_IS_NOT_VALID(HttpStatus.BAD_REQUEST, "카드 마감일이 유효하지 않습니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카드를 찾을 수 없습니다."),
    CARD_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "해당 카드에 대한 권한이 없습니다."), // TODO
    CARD_NOT_CREATED(HttpStatus.INTERNAL_SERVER_ERROR, "카드 생성에 실패했습니다."),
    CARD_NOT_UPDATED(HttpStatus.INTERNAL_SERVER_ERROR, "카드 수정에 실패했습니다."),
    CARD_NOT_DELETED(HttpStatus.INTERNAL_SERVER_ERROR, "카드 삭제에 실패했습니다."),
    CARD_NOT_MOVED_STATUS(HttpStatus.INTERNAL_SERVER_ERROR, "카드 상태 변경에 실패했습니다."),
    BUDGET_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST, "예산은 0 이상으로 입력해주세요."),
    CARD_OWNER_NOT_MATCH(HttpStatus.FORBIDDEN, "카드 소유자가 일치하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public static CardError of(String error) {
        return Arrays.stream(CardError.values())
            .filter(cardError -> cardError.name().equalsIgnoreCase(error))
            .findAny()
            .orElse(null);
    }
}
