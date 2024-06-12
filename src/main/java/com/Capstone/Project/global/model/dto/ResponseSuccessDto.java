package com.Capstone.Project.global.model.dto;

import lombok.Getter;

@Getter
public class ResponseSuccessDto {

    private final String message;

    public ResponseSuccessDto() {
        this("ok");
    }

    public ResponseSuccessDto(String message) {
        this.message = message;
    }
}

