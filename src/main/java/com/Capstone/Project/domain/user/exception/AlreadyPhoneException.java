package com.Capstone.Project.domain.user.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class AlreadyPhoneException extends LocalizedMessageException {

    public AlreadyPhoneException() {
        super(HttpStatus.BAD_REQUEST, "already.phone");
    }
}
