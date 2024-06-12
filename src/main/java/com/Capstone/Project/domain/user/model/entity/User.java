package com.Capstone.Project.domain.user.model.entity;


import com.Capstone.Project.domain.user.model.UserStatus;
import com.Capstone.Project.global.auth.role.UserRole;
import com.Capstone.Project.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private String password;
    
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


    @Builder
    private User(@NotNull String name,
                 @NotNull String nickname,
                 @NotNull String password,
                 UserRole userRole,
                 UserStatus status) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.userRole = userRole;
        this.status = status;
    }

    /**
     * User 상태를 변경합니다.
     *
     * @param status 상태
     */
    public void changeStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * 이름을 변경합니다.
     *
     * @param name 이름
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * 닉네임(아이디)을 변경합니다.
     *
     * @param nickname 닉네임(아이디)
     */
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 비밀번호를 변경합니다.
     *
     * @param encodedPassword 인코딩된 비밀번호
     */
    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }


    /**
     * Dummy 회원의 값으로 변경하기 위해서 null로 만듭니다.
     */
    public void emptyOutUserInfo() {
        this.name = "";
        this.nickname = "";
        this.password = "";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
