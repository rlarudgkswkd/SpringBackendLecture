package com.codeit.springsecuritytokenjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/* 설명. JJWT 0.12 버전 호환 JWT 예제 */
public class JjwtGenerator {

    public static void main(String[] args) {

        System.out.println("--------------------- JJWT 라이브러리 사용 ---------------------");

        /* 1. 안전한 비밀키 생성 */
		/* 1-1. HS256: 해시 기반 서명 알고리즘 (HMAC-SHA-256) */
        SecretKey secretKey = Jwts.SIG.HS256.key().build();

        /* 1-2. 비밀 키를 Base64 문자열로 인코딩
         * 비밀키를 저장/전송하기 위해 바이너리 키인 secretKey를 그저 문자열로 변환하는 것 뿐이다.
         * 따라서 아래 secretKeyString은 JWT 토큰의 구성요소가 아니다.
         * JWT 토큰의 구성 요소는 Header, Payload, Signature다.
         * */
        String secretKeyString = Encoders.BASE64.encode(secretKey.getEncoded());
        System.out.println("비밀 키: " + secretKeyString);

        /* 2. 현재 시간 기반 JWT 토큰 생성 */
		/* 2-1. 현재 시간을 나타내는 Instant 객체 생성 */
        Instant now = Instant.now();

		/* 2-2. JWT 토큰 생성 */
        String accessToken = Jwts.builder()
				// JWT 헤더 설정 (typ: "JWT" 추가)
                .header().type("JWT").and()
				// 토큰 주체(sub)
                .subject("unsuk.song")
				// 토큰 발급 시간(iat)
                .issuedAt(Date.from(Instant.now()))
				// 토큰 만료 시간(exp) : 현재 시간 기준 1시간 후
                .expiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
				// 토큰 클레임(커스텀 클레임)
                .claim("role", "INSTRUCTOR")
                .claim("animal", "OWL")
				// 토큰 서명
                .signWith(secretKey)
				// 토큰 컴팩트 형식으로 변환
                .compact();
				// compact() : 실질적으로 JWT 토큰을 생성한 후 URL 안전한 문자열로 직렬화하는 메서드.

        System.out.println("JWT 액세스 토큰: " + accessToken);

        /* 3. 토큰 검증 및 Claims 추출 */
        try {
            Claims parsedClaims = Jwts.parser()
					// 토큰 서명 검증
                    .verifyWith(secretKey).build()
					// 토큰 페이로드 추출
                    .parseSignedClaims(accessToken)
					// 토큰 페이로드 반환
                    .getPayload();

            System.out.println("====== 토큰 검증 성공 ======");
            System.out.println("usb: " + parsedClaims.getSubject());
            System.out.println("iat: " + parsedClaims.getIssuedAt());
            System.out.println("exp: " + parsedClaims.getExpiration());
            System.out.println("role: " + parsedClaims.get("role"));
            System.out.println("animal: " + parsedClaims.get("animal"));

        } catch (Exception e) {
            System.err.println("토큰 검증 실패: " + e.getMessage());
        }

        /* 4. 토큰 구조 분석 */
        analyzeJwtStructure(accessToken);
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
                byte[] headerBytes = java.util.Base64.getUrlDecoder().decode(parts[0]);
                byte[] payloadBytes = java.util.Base64.getUrlDecoder().decode(parts[1]);

                System.out.println("디코딩된 헤더: " + new String(headerBytes));
                System.out.println("디코딩된 페이로드: " + new String(payloadBytes));

            } catch (Exception e) {
                System.out.println("디코딩 과정에서 오류 발생 (정상적인 상황)");
            }
        }
    }
}