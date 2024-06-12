package com.Capstone.Project.domain.user.model.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseChangeTokenDto {
    private final String token;
}
