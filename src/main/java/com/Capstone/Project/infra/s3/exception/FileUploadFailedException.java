package com.Capstone.Project.infra.s3.exception;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class FileUploadFailedException extends LocalizedMessageException {

    public FileUploadFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "failed.file.upload");
    }
}