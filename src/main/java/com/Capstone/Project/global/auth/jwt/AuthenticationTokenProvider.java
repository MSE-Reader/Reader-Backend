package com.Capstone.Project.global.auth.jwt;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;



public interface AuthenticationTokenProvider {
    String getAccessTokenFromHeader(HttpServletRequest request);

    Authentication getAuthentication(String accessToken);
}

