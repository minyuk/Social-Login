package com.social.oauthlogin.repository;

import com.social.oauthlogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //findBy 규칙 -> Username 문법
    //select * from user where username = 1?(param) 호출
    public User findByUsername(String username);
}
