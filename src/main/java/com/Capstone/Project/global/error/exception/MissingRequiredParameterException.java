package com.Capstone.Project.global.error.exception;

import com.Capstone.Project.global.error.model.FieldErrorResult;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;
import java.util.Locale;

public class MissingRequiredParameterException extends LocalizedMessageException {
    public MissingRequiredParameterException(MissingServletRequestParameterException e) {
        super(e, HttpStatus.BAD_REQUEST, "");
    }

    @Override
    public List<Object> getMessages(MessageSource messageSource, Locale locale) {
        MissingServletRequestParameterException cause = (MissingServletRequestParameterException) getCause();
        FieldErrorResult fieldErrorResult = new FieldErrorResult(cause.getParameterName(),
                messageSource.getMessage("required.parameter", null, locale));
        return List.of(fieldErrorResult);
    }
}

