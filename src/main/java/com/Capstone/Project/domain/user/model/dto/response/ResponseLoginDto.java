package com.Capstone.Project.domain.user.model.dto.response;

import com.Capstone.Project.global.auth.jwt.AuthenticationToken;
import lombok.Getter;
import com.Capstone.Project.domain.user.model.entity.User;

@Getter
public class ResponseLoginDto {
    private final String accessToken;
    private final String refreshToken;
    private final String name;
    private final boolean isAdmin;

    public ResponseLoginDto(AuthenticationToken token, User user) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
        this.name = user.getName();
        this.isAdmin = user.getUserRole().isAdmin();
    }
}