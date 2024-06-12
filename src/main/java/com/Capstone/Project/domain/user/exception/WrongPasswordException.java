package com.Capstone.Project.domain.user.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class WrongPasswordException extends LocalizedMessageException {

    public WrongPasswordException(){
        super(HttpStatus.BAD_REQUEST, "invalid.password");
    }
}
