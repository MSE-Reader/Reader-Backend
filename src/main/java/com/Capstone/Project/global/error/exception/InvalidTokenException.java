package com.Capstone.Project.global.error.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends LocalizedMessageException {

    public InvalidTokenException() {
        super(HttpStatus.NOT_ACCEPTABLE, "invalid.token");
    }
}
