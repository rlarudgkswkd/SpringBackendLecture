package com.codeit.springsecurityjwtdemo.security;

import com.nimbusds.jose.JOSEException;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;

import com.nimbusds.jose.crypto.MACSigner;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;

import java.time.Instant;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class JwtTokenProvider {

    private final MACSigner signer;

    private final long accessTokenValiditySeconds;

    public JwtTokenProvider(
            @Value("${jwt.secret}")
            String secret,

            @Value("${jwt.access-token-validity-seconds}")
            long accessTokenValiditySeconds
    ) throws JOSEException {

        byte[] secretKey =
                Base64.getDecoder().decode(secret);

        this.signer =
                new MACSigner(secretKey);

        this.accessTokenValiditySeconds =
                accessTokenValiditySeconds;

        log.info(
                "JwtTokenProvider initialized"
        );
    }

    public String generateAccessToken(
            UserDetails userDetails
    ) {

        try {

            Instant now = Instant.now();

            Instant expiration =
                    now.plusSeconds(
                            accessTokenValiditySeconds
                    );

            List<String> authorities =
                    userDetails.getAuthorities()
                            .stream()
                            .map(
                                    GrantedAuthority::getAuthority
                            )
                            .toList();

            JWTClaimsSet claimsSet =
                    new JWTClaimsSet.Builder()

                            .subject(
                                    userDetails.getUsername()
                            )

                            .issueTime(
                                    Date.from(now)
                            )

                            .expirationTime(
                                    Date.from(expiration)
                            )

                            .jwtID(
                                    UUID.randomUUID()
                                            .toString()
                            )

                            .claim(
                                    "authorities",
                                    authorities
                            )

                            .build();

            SignedJWT signedJWT =
                    new SignedJWT(
                            new JWSHeader(
                                    JWSAlgorithm.HS256
                            ),
                            claimsSet
                    );

            signedJWT.sign(signer);

            return signedJWT.serialize();

        } catch (Exception e) {

            throw new RuntimeException(
                    "JWT 생성 실패",
                    e
            );
        }
    }
}
