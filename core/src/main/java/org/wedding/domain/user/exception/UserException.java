package org.wedding.domain.user.exception;

import org.wedding.common.exception.CommonError;
import org.wedding.common.exception.WeddingProjectException;

import lombok.Getter;

@Getter
public class UserException extends WeddingProjectException {

    private final CommonError userError;

    public UserException(CommonError userError) {
        super(userError, userError.getMessage());
        this.userError = userError;
    }

    public UserException(CommonError userError, String message) {
        super(userError, message);
        this.userError = userError;
    }

    public UserException(CommonError userError, Throwable cause) {
        super(userError, userError.getMessage(), cause);
        this.userError = userError;
    }

    public UserException(CommonError userError, String message, Throwable cause) {
        super(userError, message, cause);
        this.userError = userError;
    }
}
