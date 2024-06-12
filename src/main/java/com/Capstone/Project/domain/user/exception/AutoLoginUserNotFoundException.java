package com.Capstone.Project.domain.user.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class AutoLoginUserNotFoundException extends LocalizedMessageException {

    public AutoLoginUserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "notfound.auto-login");
    }
}

