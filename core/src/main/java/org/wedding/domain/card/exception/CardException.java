package org.wedding.domain.card.exception;

import org.wedding.common.exception.CommonError;
import org.wedding.common.exception.WeddingProjectException;

import lombok.Getter;

@Getter
public class CardException extends WeddingProjectException {

    private final CommonError cardError;

    public CardException(CardError cardError) {
        super(cardError, cardError.getMessage());
        this.cardError = cardError;
    }
}
