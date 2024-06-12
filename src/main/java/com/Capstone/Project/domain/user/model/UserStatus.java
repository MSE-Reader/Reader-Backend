package com.Capstone.Project.domain.user.model;

public enum UserStatus {
    /**
     * 활성화 계정
     */
    ACTIVE,

    /**
     * 비활성화 계정
     */
    INACTIVE;

    public boolean isActive() {
        return this == ACTIVE;
    }
}