package com.codeit.springsecuritytokenjwt2.jwt;

import com.codeit.springsecuritytokenjwt2.auth.service.CustomUserDetailsService;
import com.codeit.springsecuritytokenjwt2.jwt.store.JwtSessionRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/* 설명. 요청의 Authorization 헤더에서 Bearer Access Token을 읽어 검증하고,
 * 유효할 경우 SecurityContext에 인증 객체를 저장하는 JWT 기반 커스텀 인증 필터.
 * OncePerRequestFilter를 상속받아 디스패처 서블릿의 여러 처리 과정(포워딩, 인클루드 등)에서
 * 중복 실행되는 것을 방지하고, 하나의 HTTP 요청당 정확히 한 번만 인증 검증이 수행되도록 보장한 것이 핵심이다.
 * 이 커스텀 필터는 SecurityConfig에서 우리가 알던 UsernamePasswordAuthenticationFilter 이전에 위치시킬 것이며, 
 * 인증 실패 시 401 JSON 응답을 반환하여 클라이언트에게 명확한 오류 상태를 전달한다.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	// 토큰 검증 및 claims 추출 유틸리티
    private final JwtTokenProvider tokenProvider;
    // 사용자 로딩 서비스
    private final CustomUserDetailsService userDetailsService;
    // 에러 응답 작성용
    private final ObjectMapper objectMapper;
    // 토큰 폐기 여부 확인용 저장소
    private final JwtSessionRegistry jwtSessionRegistry;

    /**
     * JWT 인증 필터 생성자.
     * @param tokenProvider 토큰 검증 및 claims 추출 유틸리티
     * @param userDetailsService 사용자 로딩 서비스
     * @param objectMapper 에러 응답 작성용
     * @param jwtSessionRegistry 토큰 폐기 여부 확인용 저장소
     */
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   CustomUserDetailsService userDetailsService,
                                   ObjectMapper objectMapper,
                                   JwtSessionRegistry jwtSessionRegistry) {
        System.out.println("[JwtAuthenticationFilter] 생성자 호출됨: 필터 초기화");

        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.jwtSessionRegistry = jwtSessionRegistry;
    }

    /**
     * 각 요청마다 실행되어 JWT를 검증하고 인증 컨텍스트를 설정한다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("[JwtAuthenticationFilter] 요청 처리 시작: " + request.getMethod() + " " + request.getRequestURI());

            // Authorization 헤더에서 Bearer 토큰을 추출한다.
            String token = resolveToken(request);

			// 토큰이 존재하는지 확인한다.
            if (StringUtils.hasText(token)) {
                System.out.println("[JwtAuthenticationFilter] Bearer 토큰 추출 성공");

                // Access 토큰 유효성 검사(토큰 타입 검증, 만료 시간 검증, 서명 무결성 검증)
                if (tokenProvider.validateAccessToken(token)) {
                    
					// 토큰이 서버 측에서 폐기(revoked)되었는지 확인한다.
                    String jti = tokenProvider.getTokenId(token);
                    
					if (jwtSessionRegistry.isRevoked(jti)) {
                        System.out.println("[JwtAuthenticationFilter] 토큰이 폐기됨(revoked): jti=" + jti);
                        sendUnauthorized(response, "Token revoked");
                        
						return;
                    }

                    // 폐기되지 않았다면 유효한 토큰이므로 토큰 주체(subject)로 저장된 사용자명을 추출한다.
                    String username = tokenProvider.getUsernameFromToken(token);
					// 여기서 UserDetailsService를 통해 사용자 정보를 로드한다.
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    /* 설명. 추출된 사용자명으로 사용자 정보를 로드한다.
                     *  로드된 사용자 정보를 기반으로 인증 객체를 생성해 SecurityContext에 저장한다.
					 *  여기서 인증 객체는 세션 기반 인증/인가에서도 사용했었던 익숙한 UsernamePasswordAuthenticationToken 타입이다.
					 *  복습하자면, 이 객체는 인증을 마친 이후에는 주체(principal)로 사용자 정보(UserDetails)를 담고 있고,
					 *  인증 객체의 권한(authorities)으로 사용자의 권한 정보(List<GrantedAuthority>)를 담고 있다.
					 *  credentials는 비밀번호 정보를 담고 있는데, JWT 기반 인증에서는 사용하지 않는다.
                     * */
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
				
					// 인증 객체에 현재 요청 정보를 추가한다.
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// 인증 객체를 SecurityContext에 저장한다.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("[JwtAuthenticationFilter] SecurityContext 인증 설정 완료: username=" + username);
                } else {
					// 토큰 유효성 검사 실패 시 처리(401)
                    System.out.println("[JwtAuthenticationFilter] 토큰 유효성 검사 실패");
                    sendUnauthorized(response, "Invalid JWT token");
                    return;
                }
            }
        } catch (Exception e) {
			// 인증 과정에서 예외 발생 시 인증 컨텍스트를 초기화하고 401 응답을 반환한다.
            System.out.println("[JwtAuthenticationFilter] 예외 발생: " + e.getMessage());
            SecurityContextHolder.clearContext();
            sendUnauthorized(response, "JWT authentication failed");
            return;
        }

        // JWT 기반 인증 후 다음 필터로 체인을 이어간다.
        filterChain.doFilter(request, response);
    }

    /**
     * 요청의 Authorization 헤더에서 Bearer 토큰을 파싱해 반환한다.
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 401 JSON 응답을 전송한다.
     */
    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        
		// 응답 헤더 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
		
		// JSON 응답 전송
        String responseBody = objectMapper.createObjectNode()
                .put("success", false)
                .put("message", message)
                .toString();
		
		// 응답 바디 전송
        response.getWriter().write(responseBody);
    }
}


