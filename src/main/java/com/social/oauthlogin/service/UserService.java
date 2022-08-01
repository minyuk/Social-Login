package com.social.oauthlogin.service;

import com.social.oauthlogin.domain.User;
import com.social.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        User joinUser = User.builder()
                .username(user.getUsername())
                .password(encPassword)
                .email(user.getEmail())
                .role("ROLE_USER")
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .build();

        userRepository.save(joinUser);
    }




}
