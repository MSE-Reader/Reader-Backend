package com.Capstone.Project.domain.user.repository;

import com.Capstone.Project.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


    @Query("select u from User u where u.status = 'ACTIVE' and u.nickname = :nickname")
    Optional<User> findByNickname(@Param("nickname") String nickname);

    @Query("select u from User u where u.status = 'ACTIVE' and u.name = :name")
    Optional<User> findByName(String name);

    @Query("select u from User u " +
            "where u.status = 'INACTIVE' "
            + "and u.lastModifiedAt <= :inactiveDate " +
            "and u.id != :defaultUserId")
    List<User> findAllWithDeleted(@Param("inactiveDate") LocalDateTime inactiveDate, @Param("defaultUserId") Long defaultUserId);

    @Query("select u from User u where u.status = 'INACTIVE' and u.nickname = :nickname")
    Optional<User> findByInactiveByNickname(String nickname);
}
