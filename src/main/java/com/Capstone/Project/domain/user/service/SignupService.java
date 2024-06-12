package com.Capstone.Project.domain.user.service;


import com.Capstone.Project.domain.user.exception.AlreadyNameException;
import com.Capstone.Project.domain.user.exception.AlreadyNicknameException;
import com.Capstone.Project.domain.user.model.UserStatus;
import com.Capstone.Project.domain.user.model.dto.request.RequestSignupDto;
import com.Capstone.Project.domain.user.model.dto.response.ResponseSignupTokenDto;
import com.Capstone.Project.domain.user.model.entity.User;
import com.Capstone.Project.domain.user.repository.UserRepository;
import com.Capstone.Project.global.auth.role.UserRole;
import com.Capstone.Project.global.generator.SignupTokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    //TODO NicknameFilter 필요

    @Transactional
    public void signup(RequestSignupDto dto, String signupToken) {
        checkAlreadyNickname(dto.getNickname());
        checkAlreadyName(dto.getName());


        String encryptedPassword = passwordEncoder.encode(dto.getPassword());

        Optional<User> inactiveUser = userRepository.findByInactiveByNickname(dto.getNickname());

        if (inactiveUser.isPresent()) {
            User user = inactiveUser.get();
            user.changeStatus(UserStatus.ACTIVE);
            user.changeName(dto.getName());
            user.changeNickname(dto.getNickname());


        } else{
            User user = User.builder()
                    .name(dto.getName())
                    .nickname(dto.getNickname())
                    .password(encryptedPassword)


                    .userRole(UserRole.USER)
                    .status(UserStatus.ACTIVE)
                    .build();
            userRepository.save(user);
        }


    }

    public ResponseSignupTokenDto generateSignupToken() {
        String signupToken = SignupTokenGenerator.generateUUIDCode();
        return new ResponseSignupTokenDto(signupToken);
    }

    private void checkAlreadyName(String name) {
        Optional<User> alreadyUser = userRepository.findByName(name);
        if (alreadyUser.isPresent()){
            throw new AlreadyNameException();
        }
    }

    public void checkAlreadyNickname(String nickname) {
        Optional<User> alreadyUser = userRepository.findByNickname(nickname);
        if (alreadyUser.isPresent()) {
            throw new AlreadyNicknameException();
        }
    }


}
