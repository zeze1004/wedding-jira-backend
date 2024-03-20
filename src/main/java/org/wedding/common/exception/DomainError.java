package org.wedding.common.exception;

import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.user.exception.UserError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DomainError {

    USER_ERROR {
        @Override
        public UserError of(String error) { return UserError.of(error); }
    },

    CARD_ERROR {
        @Override
        public CardError of(String error) { return CardError.of(error); }
    };

    public abstract CommonError of(String error);
}
