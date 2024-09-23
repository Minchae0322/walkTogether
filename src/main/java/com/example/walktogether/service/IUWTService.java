package com.example.walktogether.service;

import com.example.walktogether.annotation.EndTimeLoggable;
import com.example.walktogether.dto.IuwtDto;
import com.example.walktogether.util.IuwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IUWTService {
    private final IuwtProvider iuwtProvider;

    @EndTimeLoggable
    public IuwtDto createIuwt(Authentication authentication) {
        return iuwtProvider.generateIUWT(authentication);
    }

}
