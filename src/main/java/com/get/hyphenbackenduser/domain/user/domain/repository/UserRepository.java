package com.get.hyphenbackenduser.domain.user.domain.repository;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
    boolean existsByName(String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findBySocialId(String socialId);
    Optional<User> findBySocialIdAndSocialType(String socialId, SocialType socialType);
}
