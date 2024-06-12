package com.Capstone.Project.domain.user.model.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseUserInfoDto {

    private final String name;
    private final String nickname;
    private final boolean isAdmin;

}
