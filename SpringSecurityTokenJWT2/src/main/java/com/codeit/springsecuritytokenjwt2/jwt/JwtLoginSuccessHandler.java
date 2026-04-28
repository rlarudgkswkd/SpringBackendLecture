package com.codeit.springsecuritytokenjwt2.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.codeit.springsecuritytokenjwt2.auth.dto.JwtDTO;
import com.codeit.springsecuritytokenjwt2.auth.dto.UserResponseDTO;
import com.codeit.springsecuritytokenjwt2.auth.service.CustomUserDetails;
import com.codeit.springsecuritytokenjwt2.jwt.store.JwtSessionRegistry;
import com.codeit.springsecuritytokenjwt2.jwt.store.JwtTokenEntity;
import com.codeit.springsecuritytokenjwt2.user.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 폼 로그인 성공 시 JWT(Access/Refresh) 발급과 응답 바디/쿠키 구성까지 담당하는 핸들러.
 * 기존 세션 기반의 LoginSuccessHandler를 대체한다.
 * 동시 로그인 제한을 위해 기존 토큰을 모두 폐기한 후 새 토큰을 발급한다.
 * Access Token은 응답 바디(JwtDto)로, Refresh Token은 HttpOnly 쿠키로 내려보낸다.
 */
@Component
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtTokenProvider tokenProvider;
    private final JwtSessionRegistry jwtSessionRegistry;

    /**
     * 로그인 성공 핸들러 생성자.
     * @param objectMapper 응답 JSON 직렬화를 위한 매퍼
     * @param tokenProvider JWT 생성/쿠키 유틸리티
     * @param jwtSessionRegistry 토큰 상태 저장소(동시 로그인 제한, 회전 상태 기록 등)
     */
    public JwtLoginSuccessHandler(ObjectMapper objectMapper, JwtTokenProvider tokenProvider, JwtSessionRegistry jwtSessionRegistry) {
		System.out.println("[JwtLoginSuccessHandler] 생성자 호출됨: 응답 JSON 직렬화를 위한 매퍼, JWT 생성/쿠키 유틸리티, 토큰 상태 저장소 주입");
        this.objectMapper = objectMapper;
        this.tokenProvider = tokenProvider;
        this.jwtSessionRegistry = jwtSessionRegistry;
    }

    /**
     * 인증 성공 시 호출된다.
     * 사용자 로그인이 성공적으로 완료되면 기존 동시 로그인 제한 정책에 따라 해당 사용자의 
     * 기존 토큰들을 모두 폐기하고, 새로운 액세스 토큰과 리프레시 토큰을 발급한다.(Token Rotation)
     * 발급된 토큰들의 메타데이터는 세션 레지스트리(JwtSessionRegistry)에 저장되며,
	 * 리프레시 토큰은 HttpOnly 쿠키로 설정하여 보안을 강화한다.
	 * 최종적으로 액세스 토큰과 사용자 정보를 포함한 JwtDTO를 JSON 형태로 클라이언트에게 응답한다.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        System.out.println("[JwtLoginSuccessHandler] onAuthenticationSuccess 시작: 응답 구성 준비");

        // 응답 인코딩/콘텐츠 타입 설정
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Principal 유효성 확인 및 캐스팅
        if (authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            try {
                // 1. 동일 계정 기존 토큰 전부 무효화(동시 로그인 제한)
                System.out.println("[JwtLoginSuccessHandler] 기존 토큰 무효화 시작 - username=" + customUserDetails.getUsername());
                jwtSessionRegistry.revokeAllByUsername(customUserDetails.getUsername());

                // 2. 새 Access/Refresh 발급
                System.out.println("[JwtLoginSuccessHandler] 새 토큰 발급 시작");
                String accessToken = tokenProvider.generateAccessToken(customUserDetails);
                String refreshToken = tokenProvider.generateRefreshToken(customUserDetails);

                // 3. 토큰 메타데이터 저장 (toEntity로 중복 제거)
                System.out.println("[JwtLoginSuccessHandler] 토큰 메타데이터 저장 시작");
                JwtTokenEntity accessEntity = tokenProvider.toEntity(accessToken);
                JwtTokenEntity refreshEntity = tokenProvider.toEntity(refreshToken);
                jwtSessionRegistry.register(accessEntity);
                jwtSessionRegistry.register(refreshEntity);

                // 4. 리프레시 쿠키 설정
                System.out.println("[JwtLoginSuccessHandler] 리프레시 쿠키 설정 시작");
                tokenProvider.addRefreshCookie(response, refreshToken);

                // 사용자 DTO 구성
                User user = customUserDetails.getUser();
                UserResponseDTO userDto = new UserResponseDTO(
                        user.getUserId(), user.getUsername(), user.getEmail(), user.getRole().name()
                );

                // 5. JwtDto 바디 전송
                JwtDTO jwtDto = new JwtDTO(userDto, accessToken);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(jwtDto));

                System.out.println("[JwtLoginSuccessHandler] onAuthenticationSuccess 완료: 응답 전송됨");
            } catch (Exception e) {
				// 예외 발생 시 처리(500)
                System.out.println("[JwtLoginSuccessHandler] 예외 발생: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(objectMapper.createObjectNode()
                        .put("success", false)
                        .put("message", "Token generation failed")
                        .toString());
            }
        } else {
			// 인증 실패 시 처리(401)
            System.out.println("[JwtLoginSuccessHandler] Invalid principal: " + authentication.getPrincipal());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.createObjectNode()
                    .put("success", false)
                    .put("message", "Invalid principal")
                    .toString());
        }
    }
}


