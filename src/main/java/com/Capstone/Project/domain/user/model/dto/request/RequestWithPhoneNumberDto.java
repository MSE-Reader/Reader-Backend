package com.Capstone.Project.domain.user.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestWithPhoneNumberDto {

    @NotBlank
    @Pattern(regexp = "01[0-1|6-9]-?\\d{4}-?\\d{4}")
    @Schema(description = "휴대폰 번호. 대시(-)는 있어도 되고 없어도 된다.", example = "010-1111-2222")
    private final String phoneNumber;
}
