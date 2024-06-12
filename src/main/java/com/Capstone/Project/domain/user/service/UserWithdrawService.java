package com.Capstone.Project.domain.user.service;

import com.Capstone.Project.domain.user.exception.UserNotFoundException;
import com.Capstone.Project.domain.user.model.UserStatus;
import com.Capstone.Project.domain.user.model.entity.User;
import com.Capstone.Project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserWithdrawService {

    private final UserRepository userRepository;

    @Transactional
    public void withdraw(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.changeStatus(UserStatus.INACTIVE);
    }
}
