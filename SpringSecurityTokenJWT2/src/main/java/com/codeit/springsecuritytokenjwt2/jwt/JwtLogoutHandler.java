package com.codeit.springsecuritytokenjwt2.jwt;

import com.codeit.springsecuritytokenjwt2.jwt.store.JwtSessionRegistry;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * JWT 환경에서의 로그아웃 처리를 담당하는 핸들러.
 * 로그아웃 요청 시 클라이언트가 전송한 Authorization 헤더에 담겨온 액세스 토큰이 있다면, 해당 토큰의 jti를 추출해 DB에서 즉시 폐기한다.
 * 또는 요청 쿠키에 담긴 리프레시 토큰이 있다면, 해당 토큰의 jti도 DB에서 즉시 폐기한다.
 * 왜냐하면 브라우저가 리프레시 토큰으로 곧바로 액세스 토큰 재발급을 시도할 수 있으므로 서버에서도 선제 차단(revoked)하기 위해서다.
 * 마지막으로 HttpOnly 리프레시 쿠키를 Max-Age=0으로 덮어쓰는 쿠키를 생성하여 즉시 만료시키는 역할을 한다.
 * 이렇게 하면 로그아웃 직후 클라이언트는 AT/RT 모두 정상 경로로 재사용할 수 없게 되고,
 * 서버 측에서도 해당 jti가 재사용되면 필터에서 즉시 차단(revoked)된다.
 */
@Component
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtTokenProvider tokenProvider;
    private final JwtSessionRegistry jwtSessionRegistry;

    /**
     * 로그아웃 핸들러 생성자.
     * @param tokenProvider 만료된 리프레시 쿠키를 생성하기 위한 프로바이더
     */
    public JwtLogoutHandler(JwtTokenProvider tokenProvider,
                            JwtSessionRegistry jwtSessionRegistry) {
        System.out.println("[JwtLogoutHandler] 생성자 호출됨: 만료된 리프레시 쿠키 생성 + 세션 레지스트리 주입");
        this.tokenProvider = tokenProvider;
        this.jwtSessionRegistry = jwtSessionRegistry;
    }

    /**
     * 로그아웃 시 호출된다.
     * 브라우저에 저장된 리프레시 토큰 쿠키를 Max-Age=0으로 덮어쓰는 쿠키를 생성하여 즉시 만료시키는 역할을 한다.
     * 세션을 사용하지 않는 JWT 구조에서 사용자의 재인증 경로를 차단하는 최소 조치다.
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        
		System.out.println("[JwtLogoutHandler] 로그아웃 처리 시작: 리프레시 쿠키 만료 응답 추가");

        /* 설명. Authorization 헤더의 AT가 있으면 DB에서 즉시 폐기한다.
         *  왜냐하면 브라우저 메모리의 AT만 지우면 다른 클라이언트에서 재사용될 수 있으므로
         *  서버 측에서도 같은 jti를 더 이상 신뢰하지 않도록 상태를 바꿔둔다.
         * */
        String authz = request.getHeader("Authorization");
        if (authz != null && authz.startsWith("Bearer ")) {
            String at = authz.substring(7);
            try {
                String atJti = tokenProvider.getTokenId(at);
                System.out.println("[JwtLogoutHandler] AT 즉시 폐기: jti=" + atJti);
				
                jwtSessionRegistry.revokeByJti(atJti);
            } catch (Exception ignored) {}
        }

        /* 설명. 쿠키의 RT가 있으면 DB에서 즉시 폐기한다.
         *  왜냐하면 브라우저가 곧바로 RT로 재발급을 시도할 수 있으므로 서버에서도 선제 차단하기 위해서다.
         * */
        if (request.getCookies() != null) {
            Optional<Cookie> rtCookie = Arrays.stream(request.getCookies())
                    .filter(c -> JwtTokenProvider.REFRESH_TOKEN_COOKIE_NAME.equals(c.getName()))
                    .findFirst();
            rtCookie.ifPresent(c -> {
                try {
                    String rtJti = tokenProvider.getTokenId(c.getValue());
                    System.out.println("[JwtLogoutHandler] RT 즉시 폐기: jti=" + rtJti);
                    jwtSessionRegistry.revokeByJti(rtJti);
                } catch (Exception ignored) {}
            });
        }

        /* 설명. 리프레시 토큰을 즉시 만료시키는 쿠키를 응답에 추가한다 (클라이언트가 보관한 RT 제거) */
        tokenProvider.expireRefreshCookie(response);

        System.out.println("[JwtLogoutHandler] 로그아웃 처리 완료: 만료 쿠키 전송");
    }
}


