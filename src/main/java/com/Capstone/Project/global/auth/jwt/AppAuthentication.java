package com.Capstone.Project.global.auth.jwt;


import com.Capstone.Project.global.auth.role.UserRole;
import org.springframework.security.core.Authentication;

public interface AppAuthentication extends Authentication {
    Long getUserId();

    UserRole getUserRole();

    boolean isAdmin();
}

