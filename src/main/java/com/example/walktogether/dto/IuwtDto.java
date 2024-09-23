package com.example.walktogether.dto;

import com.example.walktogether.vo.Iuwts;
import io.jsonwebtoken.Claims;

public record IuwtDto(
        String uuid,
        String iuwt
) {
    public static IuwtDto of(String uuid, String iuwts) {
        return new IuwtDto(uuid, iuwts);
    }
}
