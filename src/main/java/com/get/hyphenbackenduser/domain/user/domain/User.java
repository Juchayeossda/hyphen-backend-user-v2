package com.get.hyphenbackenduser.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.get.hyphenbackenduser.domain.user.enums.SocialType;
import com.get.hyphenbackenduser.domain.user.enums.UserRole;
import com.get.hyphenbackenduser.domain.user.enums.UserStatus;
import com.get.hyphenbackenduser.domain.user.exception.PasswordNotMatchException;
import com.get.hyphenbackenduser.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true, unique = true)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String image;

    @Column(nullable = true)
    private String socialId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private SocialType socialType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @PrePersist
    public void prePersist() {
        this.userStatus = this.userStatus == null ? UserStatus.ACTIVE : this.userStatus;
        this.userRole = this.userRole == null ? UserRole.MEMBER : this.userRole;
    }

    public void updateUser(String name, String email, String image) {
        this.name = name == null || name.isBlank() ? this.name : name;
        this.email = email == null || email.isBlank() ? this.email : email;
        this.image = image;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String previousPassword, String newPassword) {
        if (!passwordEncoder.matches(previousPassword, this.password)) {
            throw PasswordNotMatchException.EXCEPTION;
        }
        this.password = passwordEncoder.encode(newPassword);
    }

    public void setSocial() {
        this.userRole = UserRole.MEMBER;
    }

    public void deactivateUser() {
        this.userStatus = UserStatus.DEACTIVATED;
    }

    @Builder
    public User(String email, String password, String socialId, SocialType socialType, UserRole userRole) {
        this.email = email;
        this.password = password != null ? password : "";
        this.socialId = socialId;
        this.socialType = socialType;
        this.userRole = userRole;
    }
}
