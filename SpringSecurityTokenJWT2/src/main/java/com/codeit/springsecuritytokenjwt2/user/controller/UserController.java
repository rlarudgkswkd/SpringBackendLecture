package com.codeit.springsecuritytokenjwt2.user.controller;

import com.codeit.springsecuritytokenjwt2.auth.dto.SignupRequestDTO;
import com.codeit.springsecuritytokenjwt2.auth.dto.UserResponseDTO;
import com.codeit.springsecuritytokenjwt2.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** 
 * 사용자 관리 컨트롤러 (RESTful API)
 * 
 * 사용자 리소스에 대한 CRUD 작업을 담당하는 컨트롤러
 * RESTful API 설계 원칙에 따라 /api/users 경로를 사용한다.
 * (참고로 인증/인가 관련 API는 AuthController에서 담당한다)
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 API
     * 
     * @param signupRequest 회원가입 요청 정보
     * @return 생성된 사용자 정보
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> signup(@RequestBody SignupRequestDTO signupRequest) {
        try {
            UserResponseDTO userResponse = userService.signup(signupRequest);
            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            // 중복 사용자명/이메일 등의 경우
            return ResponseEntity.badRequest().build();
        }
    }
}