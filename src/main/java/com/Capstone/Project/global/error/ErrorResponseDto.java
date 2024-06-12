package com.Capstone.Project.global.error;

import com.Capstone.Project.global.error.exception.LocalizedMessageException;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Getter
public class ErrorResponseDto {
    private final String timestamp;
    private final String trackingId;
    private final HttpStatus status;
    private final String code;
    private final List<Object> message;

    public ErrorResponseDto(MessageSource messageSource, Locale locale, LocalizedMessageException e) {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.status = e.getStatus();
        this.code = e.getCode();
        this.message = e.getMessages(messageSource, locale);
    }
}