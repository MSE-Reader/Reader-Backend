package com.Capstone.Project.global.error.exception;

import org.springframework.http.HttpStatus;

public class IllegalTokenTypeException extends LocalizedMessageException {

    public IllegalTokenTypeException() {
        super(HttpStatus.NOT_ACCEPTABLE, "invalid.token-type");
    }
}
