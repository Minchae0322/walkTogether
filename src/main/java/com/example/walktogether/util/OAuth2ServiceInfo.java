package com.example.walktogether.util;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class OAuth2ServiceInfo {
    private Map<String, Object> attribute;

    private String userNameAttributeName;

    @Builder
    public OAuth2ServiceInfo(Map<String, Object> attribute, String userNameAttributeName) {
        this.attribute = attribute;
        this.userNameAttributeName = userNameAttributeName;
    }
}
