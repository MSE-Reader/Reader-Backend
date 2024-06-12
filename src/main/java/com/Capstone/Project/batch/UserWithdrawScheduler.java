package com.Capstone.Project.batch;

import com.Capstone.Project.domain.user.model.entity.User;
import com.Capstone.Project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserWithdrawScheduler {

    private final UserRepository userRepository;

    @Value("${app.user.default-user-id}")
    private Long defaultUserId;

    @Value("${app.user.delete-period}")
    private Period deletePeriod;

    @Scheduled(cron = "0 0 * * * *")
    public void updateInactiveUsersToDefault() {
        LocalDateTime inactiveDate = LocalDateTime.now().minus(deletePeriod);
        List<User> inactiveUsers = userRepository.findAllWithDeleted(inactiveDate, defaultUserId);

        for (User user : inactiveUsers) {
            user.emptyOutUserInfo();
        }

    }

}