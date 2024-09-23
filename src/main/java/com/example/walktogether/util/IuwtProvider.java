package com.example.walktogether.util;

import com.example.walktogether.dto.IuwtDto;
import com.example.walktogether.vo.Iuwts;
import com.example.walktogether.vo.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.walktogether.Constants.*;

@Component
public class IuwtProvider {

    public static final Long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000L;

    private final Key key;

    public IuwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public IuwtDto generateIUWT(final Authentication authentication) {
        String randomUUID = UUID.randomUUID().toString();
        Claims userClaims = generateUserClaim(authentication);

        String iuwts = Iuwts.builder()
                .setClaims(userClaims)
                .setExpiration(getTokenExpiresIn())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return IuwtDto.of(randomUUID, iuwts);
    }

    private Claims generateUserClaim(final Authentication authentication) {
        Claims claims = Iuwts.claims().setSubject(authentication.getName());

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        String authorization = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        claims.put(TOKEN_AUTHORIZATION, authorization);
        claims.put(TOKEN_USERID, userDetails.user().getId());
        claims.put(TOKEN_PROVIDER, userDetails.user().getProviderId());

        return claims;
    }

    private Date getTokenExpiresIn() {
        long now = new Date().getTime();
        return new Date(now + ACCESS_TOKEN_EXPIRATION);
    }


}
