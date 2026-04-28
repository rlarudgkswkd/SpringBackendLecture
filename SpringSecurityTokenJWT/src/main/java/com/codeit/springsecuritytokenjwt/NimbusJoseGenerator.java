package com.codeit.springsecuritytokenjwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

/* 설명. Nimbus JOSE + JWT 라이브러리를 사용한 JWT 예제 */
public class NimbusJoseGenerator {

    public static void main(String[] args) {

        System.out.println("--------------------- Nimbus 라이브러리 사용 ---------------------");

        /* 1. 안전한 비밀키 생성 */
        /* 1-1. HS256: 해시 기반 서명 알고리즘 (HMAC-SHA-256) */
        /* Nimbus에서는 최소 256비트(32바이트) 키가 필요.
		 * 참고로 만약 HS512 알고리즘을 사용하고 싶다면 512비트(64바이트) 키가 필요하다.
		 * */
        byte[] secretKey = new byte[32];
        new SecureRandom().nextBytes(secretKey);

        /* 1-2. 비밀 키를 Base64 문자열로 인코딩
         * 비밀키를 저장/전송하기 위해 바이너리 키인 secretKey를 그저 문자열로 변환하는 것 뿐이다.
         * 따라서 아래 secretKeyString은 JWT 토큰의 구성요소가 아니다.
         * JWT 토큰의 구성 요소는 Header, Payload, Signature다.
         * */
        String secretKeyString = Base64.getEncoder().encodeToString(secretKey);
        System.out.println("비밀 키: " + secretKeyString);

        /* 2. 현재 시간 기반 JWT 토큰 생성 */
        /* 2-1. 현재 시간을 나타내는 Instant 객체 생성 */
        Instant now = Instant.now();

        // 토큰 생성 시 예외처리 할 메서드가 꽤 많아서 try-catch로 감싼다.
        try {
            /* 2-2. JWT 헤더 설정 */
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    // JWT 헤더 설정 (typ: "JWT" 추가)
                    .type(JOSEObjectType.JWT)
                    .build();

            /* 2-3. JWT 클레임 설정 */
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
					// 토큰 주체(sub)
                    .subject("unsuk.song")
					// 토큰 발급 시간(iat)
                    .issueTime(Date.from(now))
					// 토큰 만료 시간(exp) : 현재 시간 기준 1시간 후
                    .expirationTime(Date.from(now.plus(1, ChronoUnit.HOURS)))
					// 토큰 클레임(커스텀 클레임)
                    .claim("role", "INSTRUCTOR")
                    .claim("animal", "OWL")
                    .build();

            /* 2-4. JWT 토큰 생성 및 서명 */
			// 토큰 생성: 준비된 헤더와 클레임을 사용하여 토큰 생성.
            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
			// 토큰 서명자: MAC 알고리즘을 사용하여 토큰 서명자 생성.
            JWSSigner signer = new MACSigner(secretKey);
			// 토큰 서명: 토큰 생성 후 서명.
            signedJWT.sign(signer);

            /* 2-5. 토큰을 컴팩트 형식으로 변환 */
            String accessToken = signedJWT.serialize();
            // serialize() : 실질적으로 JWT 토큰을 생성한 후 URL 안전한 문자열로 직렬화하는 메서드.

            System.out.println("JWT 액세스 토큰: " + accessToken);

            /* 3. 토큰 검증 및 Claims 추출 */
            try {
                /* 3-1. 토큰 파싱 */
                SignedJWT parsedJWT = SignedJWT.parse(accessToken);

                /* 3-2. 토큰 서명 검증 */
                JWSVerifier verifier = new MACVerifier(secretKey);
                if (parsedJWT.verify(verifier)) {
                    /* 3-3. 토큰 페이로드 추출 */
                    JWTClaimsSet parsedClaims = parsedJWT.getJWTClaimsSet();

                    System.out.println("====== 토큰 검증 성공 ======");
                    System.out.println("sub: " + parsedClaims.getSubject());
                    System.out.println("iat: " + parsedClaims.getIssueTime());
                    System.out.println("exp: " + parsedClaims.getExpirationTime());
                    System.out.println("role: " + parsedClaims.getClaim("role"));
                    System.out.println("animal: " + parsedClaims.getClaim("animal"));
                } else {
                    System.err.println("토큰 서명 검증 실패");
                }

            } catch (Exception e) {
                System.err.println("토큰 검증 실패: " + e.getMessage());
            }

            /* 4. 토큰 구조 분석 */
            analyzeJwtStructure(accessToken);

        } catch (Exception e) {
            System.err.println("JWT 토큰 생성 실패: " + e.getMessage());
        }
    }

    private static void analyzeJwtStructure(String jwt) {

        System.out.println("====== JWT 구조 분석 ======");
        String[] parts = jwt.split("\\.");

        if (parts.length == 3) {
            System.out.println("헤더 부분: " + parts[0]);
            System.out.println("페이로드 부분: " + parts[1]);
            System.out.println("서명 부분: " + parts[2]);

            // Base64URL 디코딩으로 내용 확인 가능
            try {
                byte[] headerBytes = Base64.getUrlDecoder().decode(parts[0]);
                byte[] payloadBytes = Base64.getUrlDecoder().decode(parts[1]);

                System.out.println("디코딩된 헤더: " + new String(headerBytes));
                System.out.println("디코딩된 페이로드: " + new String(payloadBytes));

            } catch (Exception e) {
                System.out.println("디코딩 과정에서 오류 발생 (정상적인 상황)");
            }
        }
    }
}
