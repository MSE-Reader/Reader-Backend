package com.Capstone.Project.domain.user.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class AlreadyNameException extends LocalizedMessageException {

    public AlreadyNameException(){
        super(HttpStatus.BAD_REQUEST, "already.name");
    }
}
