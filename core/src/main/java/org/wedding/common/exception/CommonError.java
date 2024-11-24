package org.wedding.common.exception;

import org.springframework.http.HttpStatus;

import jakarta.validation.Payload;

public interface CommonError extends Payload {

    HttpStatus getHttpStatus();

    String getMessage();
}
