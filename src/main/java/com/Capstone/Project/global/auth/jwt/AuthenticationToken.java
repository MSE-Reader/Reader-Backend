package com.Capstone.Project.global.auth.jwt;

public interface AuthenticationToken {
    String getAccessToken();

    String getRefreshToken();
}

