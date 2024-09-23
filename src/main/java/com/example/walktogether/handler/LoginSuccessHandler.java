package com.example.walktogether.handler;


import com.example.walktogether.annotation.EndTimeLoggable;
import com.example.walktogether.dto.IuwtDto;
import com.example.walktogether.service.IuwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

import static com.example.walktogether.Constants.TOKEN_EXPIRE_TIME;
import static com.example.walktogether.util.IuwtProvider.ACCESS_TOKEN_EXPIRATION;


@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final IuwtService iuwtService;

    @Override
    @EndTimeLoggable
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final IuwtDto iuwtDto = iuwtService.createIuwt(authentication);
        final Cookie cookie = setCookie(iuwtDto);

        response.setHeader("Authorization", iuwtDto.iuwt());
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private Cookie setCookie(IuwtDto iuwtDto) {
        Cookie cookie = new Cookie("Authorization", iuwtDto.uuid());
        cookie.setHttpOnly(true);

        cookie.setSecure(true);

        cookie.setPath("/");

        cookie.setMaxAge(Math.toIntExact(ACCESS_TOKEN_EXPIRATION));
        return cookie;
    }


}
