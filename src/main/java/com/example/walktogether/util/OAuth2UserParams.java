package com.example.walktogether.util;

import com.example.walktogether.type.UserRole;
import com.example.walktogether.vo.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Random;

@Getter
@RequiredArgsConstructor
public class OAuth2UserParams {

    private String providerId;

    private String username;

    private Map<String, Object> attributes;

    @Builder
    public OAuth2UserParams(String providerId, String username, Map<String, Object> attributes) {
        this.providerId = providerId;
        this.username = username;
        this.attributes = attributes;
    }

    public User createUser(OAuth2UserParams oAuth2UserParams) {
        Random random = new Random();
        String randomPassword = String.valueOf(random.nextInt(1000000) + 1);

        return User.builder()
                .providerId(oAuth2UserParams.providerId)
                .password(randomPassword)
                .username(oAuth2UserParams.getUsername())
                .role(UserRole.USER)
                .build();
    }
}
