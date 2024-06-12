package com.Capstone.Project.domain.user.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestLoginDto {

    @NotBlank
    @Schema(description = "닉네임", example = "user1")
    private final String nickname;

    @NotBlank
    @Schema(description = "비밀번호", example = "12345678")
    private final String password;

}