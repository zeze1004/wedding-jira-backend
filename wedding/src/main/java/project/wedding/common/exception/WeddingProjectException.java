package project.wedding.common.exception;

import lombok.Getter;

@Getter
public class WeddingProjectException extends RuntimeException {

    private final CommonError commonError;

    public WeddingProjectException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    public WeddingProjectException(CommonError commonError, String message) {
        super(message);
        this.commonError = commonError;
    }

    public WeddingProjectException(CommonError commonError, Throwable cause) {
        super(commonError.getMessage(), cause);
        this.commonError = commonError;
    }

    public WeddingProjectException(CommonError commonError, String message, Throwable cause) {
        super(message, cause);
        this.commonError = commonError;
    }
}
