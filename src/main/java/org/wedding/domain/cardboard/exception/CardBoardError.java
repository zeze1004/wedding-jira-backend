package org.wedding.domain.cardboard.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.wedding.common.exception.CommonError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardBoardError implements CommonError {

    CARD_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카드보드를 찾을 수 없습니다."),
    CARD_NOT_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, "카드 저장에 실패했습니다."),
    CARD_NOT_LOAD(HttpStatus.INTERNAL_SERVER_ERROR, "카드 불러오기에 실패했습니다."),
    CARD_OWNER_NOT_MATCH(HttpStatus.FORBIDDEN, "카드 소유자가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public static CardBoardError of(String error) {
        return Arrays.stream(CardBoardError.values())
            .filter(cardBoardError -> cardBoardError.name().equalsIgnoreCase(error))
            .findAny()
            .orElse(null);
    }
}
