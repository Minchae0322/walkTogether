package com.example.walktogether.service;


import com.example.walktogether.LogAspect;
import com.example.walktogether.repo.UserRepository;
import com.example.walktogether.type.OAuth2Attribute;
import com.example.walktogether.util.OAuth2ServiceInfo;
import com.example.walktogether.util.OAuth2UserParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OAuth2DetailsServiceImplTest {

    @InjectMocks
    private OAuth2DetailsServiceImpl oAuth2DetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OAuth2UserRequest oAuth2UserRequest;

    @Mock
    private OAuth2User oAuth2User;

    @SpyBean
    private LogAspect logAspect;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUser_Success() {
        // Given
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "123456789");
        attributes.put("email", "user@example.com");
        attributes.put("name", "Test User");

        when(oAuth2UserRequest.getClientRegistration()).thenReturn(mockClientRegistration());
        when(oAuth2User.getAttributes()).thenReturn(attributes);

        // When
        OAuth2User result = oAuth2DetailsService.loadUser(oAuth2UserRequest);

        // Then
        assertNotNull(result);
    }

    private ClientRegistration mockClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .userNameAttributeName("sub")
                .build();
    }
}