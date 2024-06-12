package com.Capstone.Project.domain.user.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends LocalizedMessageException {

    public BadRequestException(Throwable t) {
        super(t, HttpStatus.BAD_REQUEST, "invalid.request");
    }
}