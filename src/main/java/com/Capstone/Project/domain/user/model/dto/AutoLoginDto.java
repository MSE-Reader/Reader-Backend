package com.Capstone.Project.domain.user.model.dto;

import com.Capstone.Project.global.auth.role.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AutoLoginDto {

    private final String userId;
    private final UserRole userRole;
}
