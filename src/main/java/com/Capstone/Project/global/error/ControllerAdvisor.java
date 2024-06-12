package com.Capstone.Project.global.error;


import com.Capstone.Project.domain.user.exception.BindingFailedException;
import com.Capstone.Project.domain.user.exception.BadRequestException;
import com.Capstone.Project.domain.user.exception.DuplicateDataException;
import com.Capstone.Project.domain.user.exception.NotSupportedMethodException;
import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import com.Capstone.Project.global.error.exception.MissingRequiredParameterException;
import com.Capstone.Project.global.error.exception.NotGrantedException;
import com.Capstone.Project.global.error.service.ErrorLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ControllerAdvisor {

    private final MessageSource messageSource;
    private final ErrorLogService errorLogService;

    @Value("${app.enable-test-controller}")
    private boolean isEnabledTest;

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> localizedException(LocalizedMessageException e, Locale locale) {
        ErrorResponseDto dto = new ErrorResponseDto(messageSource, locale, e);
        log.error("A problem has occurred in controller advice: [id={}]", dto.getTrackingId(), e);
        return filter(e, ResponseEntity.status(e.getStatus()).body(dto));
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badParameter(HttpMessageNotReadableException e, Locale locale) {
        return localizedException(new BadRequestException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badParameter(BindException e, Locale locale) {
        return localizedException(new BindingFailedException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badParameter(MethodArgumentTypeMismatchException e, Locale locale) {
        return localizedException(new BadRequestException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badParameter(MissingServletRequestParameterException e, Locale locale) {
        return localizedException(new MissingRequiredParameterException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badMethod(HttpRequestMethodNotSupportedException e, Locale locale) {
        return localizedException(new NotSupportedMethodException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> accessDenied(AccessDeniedException e, Locale locale) {
        return localizedException(new NotGrantedException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> dataDuplicateException(DataIntegrityViolationException e, Locale locale) {
        return localizedException(new DuplicateDataException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> unexpectedException(Exception e, Locale locale) {
        ErrorResponseDto dto = new ErrorResponseDto(messageSource, locale, LocalizedMessageException.of(e));
        log.error("Unexpected exception has occurred in controller advice: [id={}]", dto.getTrackingId(), e);
        return filter(e, ResponseEntity.internalServerError().body(dto));
    }

    private ResponseEntity<ErrorResponseDto> filter(Throwable t, ResponseEntity<ErrorResponseDto> entity) {
        ErrorResponseDto dto = entity.getBody();
        if (isEnabledTest && dto != null) {
            errorLogService.logError(dto.getTrackingId(), t, dto);
        }
        return entity;
    }
}
