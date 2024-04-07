package org.wedding.domain.cardboard.exception;

import org.wedding.common.exception.CommonError;
import org.wedding.common.exception.WeddingProjectException;

import lombok.Getter;

@Getter
public class CardBoardException extends WeddingProjectException {

    private final CommonError cardBoardError;

    public CardBoardException(CardBoardError cardBoardError) {
        super(cardBoardError, cardBoardError.getMessage());
        this.cardBoardError = cardBoardError;
    }
}
