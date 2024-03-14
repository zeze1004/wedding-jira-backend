package org.wedding.common.exception;

import org.wedding.domain.user.exception.UserError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DomainError {

    USER_ERROR {
        @Override
        public UserError of(String error) {
            return UserError.of(error);
        }
    };

    public abstract CommonError of(String error);
}
