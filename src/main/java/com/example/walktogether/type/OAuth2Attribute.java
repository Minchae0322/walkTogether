package com.example.walktogether.type;

import com.example.walktogether.util.OAuth2ServiceInfo;
import com.example.walktogether.util.OAuth2UserParams;

import java.util.Arrays;
import java.util.function.Function;

public enum OAuth2Attribute {
    GOOGLE("google", (oAuth2ServiceInfo) -> OAuth2UserParams.builder()
            .providerId("google")
            .username((String) oAuth2ServiceInfo.getAttribute().get(oAuth2ServiceInfo.getUserNameAttributeName()))
            .attributes(oAuth2ServiceInfo.getAttribute())
            .build()
    ),;

    private final String providerId;
    private final Function<OAuth2ServiceInfo, OAuth2UserParams> profileBuilder;

    OAuth2Attribute(String providerId, Function<OAuth2ServiceInfo, OAuth2UserParams> profileBuilder) {
        this.providerId = providerId;
        this.profileBuilder = profileBuilder;
    }

    public String getProviderId() {
        return providerId;
    }

    public static OAuth2UserParams createOAuth2UserParams(String providerId, OAuth2ServiceInfo oAuth2ServiceInfo) {
        return Arrays.stream(values())
                .filter(oAuth2Attribute -> oAuth2Attribute.providerId.equals(providerId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .profileBuilder.apply(oAuth2ServiceInfo);
    }
}
