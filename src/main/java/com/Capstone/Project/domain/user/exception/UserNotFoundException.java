package com.Capstone.Project.domain.user.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends LocalizedMessageException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "notfound.user");
    }
}