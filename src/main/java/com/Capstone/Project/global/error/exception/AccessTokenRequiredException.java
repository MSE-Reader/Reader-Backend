package com.Capstone.Project.global.error.exception;

import org.springframework.http.HttpStatus;

public class AccessTokenRequiredException extends LocalizedMessageException {

    public AccessTokenRequiredException(){
        super(HttpStatus.UNAUTHORIZED, "required.access-token");
    }
}
