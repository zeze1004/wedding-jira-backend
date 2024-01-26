package project.wedding.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.wedding.user.exception.UserError;

@Getter
@AllArgsConstructor
public enum ErrorHandler {

    USER_ERROR {
        @Override
        public UserError of(String error) {
            return UserError.of(error);
        }
    };

    public abstract CommonError of(String error);
}
