package com.Capstone.Project.domain.user.controller;


import com.Capstone.Project.domain.user.model.dto.request.RequestLoginDto;
import com.Capstone.Project.domain.user.model.dto.request.RequestReissueDto;
import com.Capstone.Project.domain.user.model.dto.request.RequestSignupDto;
import com.Capstone.Project.domain.user.model.dto.response.ResponseLoginDto;
import com.Capstone.Project.domain.user.model.dto.response.ResponseReissueDto;
import com.Capstone.Project.domain.user.model.dto.response.ResponseSignupTokenDto;
import com.Capstone.Project.domain.user.model.dto.response.ResponseUserInfoDto;
import com.Capstone.Project.domain.user.service.SignupService;
import com.Capstone.Project.domain.user.service.UserService;
import com.Capstone.Project.global.auth.jwt.AppAuthentication;
import com.Capstone.Project.global.auth.role.UserAuth;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "사용자", description = "사용자 관련 api")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final SignupService signupService;


    /**
     * 회원가입 토큰 생성
     *
     * @return 회원가입 토큰
     */
    @GetMapping("/signup-token")
    public ResponseSignupTokenDto generateSignupToken() {
        return signupService.generateSignupToken();
    }



    /**
     * 회원가입
     *
     * @param dto         요청 body
     * @param signupToken 회원가입 토큰
     *                    <p>
     *                    새로운 유저가 회원가입하거나 상태가 INACTIVE인 유저가 회원가입을 할 때 사용됩니다.
     */
    @PostMapping("/{signup-token}")
    public void signup(@Valid @RequestBody RequestSignupDto dto,
                       @PathVariable("signup-token") String signupToken) {
        signupService.signup(dto, signupToken);
    }

    /**
     * 닉네임 중복 확인
     *
     * @param nickname 닉네임
     */
    @PostMapping("/signup/verify/{nickname}")
    public void verifyNickname(@PathVariable("nickname") String nickname) {
        signupService.checkAlreadyNickname(nickname);
    }

    /**
     * 로그인
     *
     * @param dto 요청 body
     * @return 로그인 인증 정보
     */
    @PostMapping("/login")
    public ResponseLoginDto login(@Valid @RequestBody RequestLoginDto dto) {
        return userService.login(dto);
    }

    /**
     * 토큰 재발급
     *
     * @param dto           요청 body
     * @return              새로 발급된 토큰
     */
    @UserAuth
    @PostMapping("/reissue")
    public ResponseReissueDto reissue(HttpServletRequest request,
                                      @Valid @RequestBody RequestReissueDto dto) {
        return userService.reissue(request, dto.getRefreshToken());
    }

    /**
     * 내 정보 조회
     *
     * @param auth         인증 정보
     */
    @GetMapping
    @UserAuth
    public ResponseUserInfoDto getUserInfo(AppAuthentication auth) {
        return userService.getUserInfo(auth.getUserId());
    }



}