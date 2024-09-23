package com.example.walktogether.handler;


import com.example.walktogether.annotation.EndTimeLoggable;
import com.example.walktogether.dto.IuwtDto;
import com.example.walktogether.service.IUWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;


@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final IUWTService iuwtService;

    @Override
    @EndTimeLoggable
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final IuwtDto iuwtDto = iuwtService.createIuwt(authentication);
        final Cookie cookie = setCookie(iuwtDto);

        response.setHeader("Authorization", iuwtDto.iuwt());
        response.addCookie(cookie);
    }

    private Cookie setCookie(IuwtDto iuwtDto) {
        Cookie cookie = new Cookie("Authorization", iuwtDto.iuwt());
        cookie.setHttpOnly(true);

        // Secure 설정 (HTTPS에서만 사용 가능)
        cookie.setSecure(true); // HTTPS 사용시 true로 설정

        // 쿠키 경로 설정 ("/"로 설정하면 애플리케이션 전역에서 사용 가능)
        cookie.setPath("/");

        // 쿠키의 만료 시간 설정 (예: 7일)
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 쿠키 유효
        return cookie;
    }


}
