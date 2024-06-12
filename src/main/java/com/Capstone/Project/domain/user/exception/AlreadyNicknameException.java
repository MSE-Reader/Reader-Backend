package com.Capstone.Project.domain.user.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class AlreadyNicknameException extends LocalizedMessageException {

    public AlreadyNicknameException() {
        super(HttpStatus.BAD_REQUEST, "already.nickname");
    }
}