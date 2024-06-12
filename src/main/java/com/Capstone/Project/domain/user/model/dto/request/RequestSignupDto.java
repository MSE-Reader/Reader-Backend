package com.Capstone.Project.domain.user.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestSignupDto {

    @NotBlank
    @Schema(description = "이름", example = "홍길동")
    private final String name;

    @NotBlank
    @Size(min = 3, max = 16)
    @Pattern(regexp = "^(?!.*\\s{2,})[A-Za-z\\dㄱ-ㅎㅏ-ㅣ가-힣_ ]{3,16}$")
    @Schema(description = "닉네임", example = "user1")
    private final String nickname;


    @NotBlank
    @Size(min = 3, max = 200)
    @Schema(description = "비밀번호", example = "12345678")
    private final String password;
}
