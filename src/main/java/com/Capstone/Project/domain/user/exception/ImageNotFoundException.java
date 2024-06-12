package com.Capstone.Project.domain.user.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class ImageNotFoundException extends LocalizedMessageException {

    public ImageNotFoundException(){
        super(HttpStatus.NOT_FOUND, "notfound.image");
    }
}
