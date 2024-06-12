package com.Capstone.Project.domain.user.service;


import com.Capstone.Project.domain.user.exception.UserNotFoundException;
import com.Capstone.Project.domain.user.exception.WrongPasswordException;
import com.Capstone.Project.domain.user.model.dto.AutoLoginDto;
import com.Capstone.Project.domain.user.model.dto.request.RequestLoginDto;
import com.Capstone.Project.domain.user.model.dto.response.ResponseLoginDto;
import com.Capstone.Project.domain.user.model.dto.response.ResponseReissueDto;
import com.Capstone.Project.domain.user.model.dto.response.ResponseUserInfoDto;
import com.Capstone.Project.domain.user.model.entity.User;
import com.Capstone.Project.domain.user.repository.AutoLoginRepository;
import com.Capstone.Project.domain.user.repository.UserRepository;
import com.Capstone.Project.global.auth.jwt.AuthenticationToken;
import com.Capstone.Project.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    public static final String AUTO_LOGIN_NAME = "auto-login";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AutoLoginRepository autoLoginRepository;

    public ResponseLoginDto login(RequestLoginDto dto) {
        Instant now = Instant.now();
        User user = userRepository.findByNickname(dto.getNickname())
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            AuthenticationToken token = jwtProvider.issue(user);
            autoLoginRepository.setAutoLoginPayload(token.getRefreshToken(), AUTO_LOGIN_NAME,
                    new AutoLoginDto(user.getId().toString(), user.getUserRole()), now);
            return new ResponseLoginDto(token, user);
        } else {
            throw new WrongPasswordException();
        }
    }

    public ResponseReissueDto reissue(HttpServletRequest request, String refreshToken) {
        String accessToken = jwtProvider.getAccessTokenFromHeader(request);
        AuthenticationToken token = jwtProvider.reissue(accessToken, refreshToken);
        return new ResponseReissueDto(token);
    }

    public ResponseUserInfoDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return new ResponseUserInfoDto(user.getName(), user.getNickname(),
                 user.getUserRole().isAdmin());
    }


}