package com.Capstone.Project.domain.user.repository;

import java.time.Instant;
import java.util.Optional;

public interface AutoLoginRepository {

    /**
     * 리프레시 토큰을 키로, 자동 로그인 정보를 저장합니다.
     *
     * @param refreshToken      리프레시 토큰
     * @param autoLoginName     자동 로그인 정보 이름 (구분자)
     * @param data              자동 로그인 정보 데이터
     */
    void setAutoLoginPayload(String refreshToken, String autoLoginName, Object data, Instant now);

    /**
     * 리프레시 토큰을 키로, 자동 로그인 정보를 조회합니다.
     *
     * @param refreshToken      리프레시 토큰
     * @param autoLoginName     자동 로그인 정보 이름 (구분자)
     * @return                  자동 로그인 정보 데이터
     */
    <T> Optional<T> getAutoLoginPayload(String refreshToken, String autoLoginName, Class<T> payloadClass, Instant now);
}
