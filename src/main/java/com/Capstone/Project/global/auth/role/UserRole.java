package com.Capstone.Project.global.auth.role;

import com.Capstone.Project.global.auth.jwt.AppAuthentication;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.Capstone.Project.global.auth.role.UserAuthNames.*;

@Getter
public enum UserRole {
    USER(ROLE_USER),
    GUEST(ROLE_GUEST),
    ADMIN(combine(ROLE_ADMIN, ROLE_USER));

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    private static final Map<String, UserRole> BY_LABEL =
            Stream.of(values()).collect(Collectors.toMap(UserRole::getName, e -> e));

    public static UserRole of(String name) {
        return BY_LABEL.get(name);
    }

    public static UserRole from(AppAuthentication auth) {
        if (auth == null) {
            return GUEST;
        }
        return auth.getUserRole();
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}