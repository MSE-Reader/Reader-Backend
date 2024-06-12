package com.Capstone.Project.domain.user.model.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@RequiredArgsConstructor(access = PROTECTED)
public class RequestReissueDto {

    @NotBlank
    private final String refreshToken;

}
