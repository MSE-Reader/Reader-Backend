package com.Capstone.Project.global.auth.jwt;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtAuthenticationToken implements AuthenticationToken{
    private String accessToken;
    private String refreshToken;
}
