package com.codeit.springsecuritytokenjwt2.auth.controller;

import com.codeit.springsecuritytokenjwt2.auth.dto.JwtDTO;
import com.codeit.springsecuritytokenjwt2.auth.dto.RoleUpdateRequestDTO;
import com.codeit.springsecuritytokenjwt2.auth.dto.UserResponseDTO;
import com.codeit.springsecuritytokenjwt2.auth.service.CustomUserDetails;
import com.codeit.springsecuritytokenjwt2.auth.service.CustomUserDetailsService;
import com.codeit.springsecuritytokenjwt2.jwt.JwtTokenProvider;
import com.codeit.springsecuritytokenjwt2.jwt.store.JwtSessionRegistry;
import com.codeit.springsecuritytokenjwt2.jwt.store.JwtTokenEntity;
import com.codeit.springsecuritytokenjwt2.user.entity.User;
import com.codeit.springsecuritytokenjwt2.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

    /** 
     * 인증 및 인가 컨트롤러
     * <p>
     * 인증(Authentication)과 인가(Authorization) 관련 API를 제공하는 컨트롤러
     * 사용자 리소스 CRUD는 UserController에서 담당한다.
     */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final JwtSessionRegistry jwtSessionRegistry;

    public AuthController(UserService userService,
                          JwtTokenProvider jwtTokenProvider,
                          CustomUserDetailsService userDetailsService,
                          JwtSessionRegistry jwtSessionRegistry) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.jwtSessionRegistry = jwtSessionRegistry;
    }

    /**
     * CSRF 토큰 발급 API
     * <p>
     * 프론트엔드에서 CSRF 토큰을 받아올 수 있도록 제공하는 엔드포인트.
     * Spring Security가 자동으로 CsrfToken 객체를 주입해준다.
     * 
     * @param csrfToken Spring Security가 자동 주입하는 CSRF 토큰
     * @return CSRF 토큰 정보
     */
    @GetMapping("/csrf-token")
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {

        System.out.println("[AuthController] ========== CSRF 토큰 발급 요청 시작 ==========");
        System.out.println("[AuthController] 파라미터 이름: " + csrfToken.getParameterName());
        System.out.println("[AuthController] 헤더 이름: " + csrfToken.getHeaderName());
        System.out.println("[AuthController] 토큰 값: " + csrfToken.getToken());
        System.out.println("[AuthController] ========== CSRF 토큰 발급 완료 ==========");

        return ResponseEntity.noContent().build(); // 204 Void 응답
    }

    /**
     * 리프레시 토큰으로 액세스 토큰 재발급
     * <p>
     * 리프레시 토큰을 사용해 액세스 토큰을 재발급하는 API.
     * 리프레시 토큰은 쿠키에 저장되어 있으며, 이를 통해 액세스 토큰을 재발급한다.
     * 
     * @param refreshToken 리프레시 토큰
     * @param response HTTP 응답 객체
     * @return 재발급된 액세스 토큰
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtDTO> refresh(
			/* 설명. @CookieValue 어노테이션을 사용하면 HTTP 요청 헤더(Cookie)의 쿠키 값을 자동으로 추출해준다. */
            @CookieValue(
                    // 쿠키 이름은 JwtTokenProvider 클래스에 정의된 상수 REFRESH_TOKEN_COOKIE_NAME을 사용한다.
                    name = JwtTokenProvider.REFRESH_TOKEN_COOKIE_NAME,
                    // 쿠키 값이 없어도 에러를 발생시키지 않고 null을 반환한다. 그러면 아래 코드에서 쿠키 값이 null인지 확인하고 400 응답을 반환한다.
                    required = false
            )
            String refreshToken,
            HttpServletResponse response) {

		// 쿠키 값이 없거나(null) 유효하지 않으면 400 응답을 반환한다.
        if (refreshToken == null || !jwtTokenProvider.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

		// 쿠키 값이 유효하면 쿠키 값을 추출한다.
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        String oldRefreshJti = jwtTokenProvider.getTokenId(refreshToken);

        // 사용자 로드
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        try {
            // 새 토큰 발급
            String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

            // 회전 처리: 이전 리프레시 무효화 및 교체 표시
            String newRefreshJti = jwtTokenProvider.getTokenId(newRefreshToken);
			
            /* 설명. 이전 RT가 이미 폐기된 경우(동시 로그인 정책 등) 재사용을 차단한다.
             *  왜냐하면 동일 계정 재로그인 시 기존 토큰들이 revoke 처리되므로,
             *  기존 브라우저가 가진 RT로 재발급을 시도하면 여기서 차단해야 자동 로그아웃이 보장된다.
             * */
            if (jwtSessionRegistry.isRevoked(oldRefreshJti)) {
                // 쿠키도 만료 처리: 클라이언트 보관 RT 제거
                jwtTokenProvider.expireRefreshCookie(response);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            jwtSessionRegistry.markReplaced(oldRefreshJti, newRefreshJti);

            // 신규 토큰 메타데이터 저장
            JwtTokenEntity accessEntity = jwtTokenProvider.toEntity(newAccessToken);
            JwtTokenEntity refreshEntity = jwtTokenProvider.toEntity(newRefreshToken);
            jwtSessionRegistry.register(accessEntity);
            jwtSessionRegistry.register(refreshEntity);

            // 리프레시 쿠키 교체
            // HTTP 응답 헤더(Set-Cookie)에 리프레시 쿠키를 추가한다.
            jwtTokenProvider.addRefreshCookie(response, newRefreshToken);

			// 응답 바디 구성
            User user = userDetails.getUser();
            UserResponseDTO userDto = new UserResponseDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getRole().name());
            JwtDTO body = new JwtDTO(userDto, newAccessToken);

			// 응답 바디 전송
            return ResponseEntity.ok(body);
        } catch (Exception e) {
			// 리프레시 토큰 발급 도중 발생한 예외 처리(500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사용자 권한 변경 API
     * <p>
     * 사용자의 권한을 변경하는 API (ADMIN 권한 필요)
     * 
     * @param roleUpdateRequest 권한 변경 요청 정보 (userId, newRole)
     * @return 업데이트된 사용자 정보
     */
    @PutMapping("/role")
    public ResponseEntity<UserResponseDTO> updateUserRole(
            @RequestBody RoleUpdateRequestDTO roleUpdateRequest) {
        
        System.out.println("[AuthController] 사용자 권한 변경 요청");
        System.out.println("[AuthController] 요청 데이터: " + roleUpdateRequest);
        
        try {
            UserResponseDTO userResponse = userService.updateUserRole(
                roleUpdateRequest.getUserId(), 
                roleUpdateRequest.getNewRole()
            );
            
            System.out.println("[AuthController] 권한 변경 성공: " + userResponse);
            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            System.out.println("[AuthController] 권한 변경 실패: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
} 