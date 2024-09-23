package com.example.walktogether.service;

import com.example.walktogether.annotation.EndTimeLoggable;
import com.example.walktogether.repo.UserRepository;
import com.example.walktogether.type.OAuth2Attribute;
import com.example.walktogether.util.OAuth2ServiceInfo;
import com.example.walktogether.util.OAuth2UserParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2DetailsServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    @EndTimeLoggable
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        OAuth2UserParams oAuth2UserParams = extractOAuth2UserParams(userRequest, oAuth2User);

        return (OAuth2User) getUserElseCreate(oAuth2UserParams);
    }

    private OAuth2UserParams extractOAuth2UserParams(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String providerId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = oAuth2UserRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2ServiceInfo oAuth2ServiceInfo = OAuth2ServiceInfo.builder()
                .userNameAttributeName(userNameAttributeName)
                .attribute(oAuth2User.getAttributes())
                .build();

        return OAuth2Attribute.createOAuth2UserParams(providerId, oAuth2ServiceInfo);
    }

    private UserDetails getUserElseCreate(OAuth2UserParams oAuth2UserParams) {
        return (UserDetails) userRepository.findByUsernameAndProviderId(oAuth2UserParams.getUsername(), oAuth2UserParams.getProviderId())
                .orElseGet(() -> userRepository.save(oAuth2UserParams.createUser(oAuth2UserParams)));
    }
}
