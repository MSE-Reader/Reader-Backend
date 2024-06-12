package com.Capstone.Project.global.error.exception;

import org.springframework.http.HttpStatus;

public class NotGrantedException extends LocalizedMessageException {

    public NotGrantedException() {
        super(HttpStatus.FORBIDDEN, "required.granted");
    }

    public NotGrantedException(Throwable t) {
        super(t, HttpStatus.FORBIDDEN, "required.granted");
    }
}