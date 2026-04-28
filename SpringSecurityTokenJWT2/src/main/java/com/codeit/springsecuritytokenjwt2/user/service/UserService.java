package com.codeit.springsecuritytokenjwt2.user.service;

import com.codeit.springsecuritytokenjwt2.auth.dto.SignupRequestDTO;
import com.codeit.springsecuritytokenjwt2.auth.dto.UserResponseDTO;
import com.codeit.springsecuritytokenjwt2.jwt.store.JwtSessionRegistry;
import com.codeit.springsecuritytokenjwt2.user.entity.Role;
import com.codeit.springsecuritytokenjwt2.user.entity.User;
import com.codeit.springsecuritytokenjwt2.user.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 서비스
 * 
 * 사용자 도메인의 CRUD 비즈니스 로직을 담당하는 서비스
 * 인증/인가 관련 로직은 AuthService로 분리하여 단일 책임 원칙을 준수한다.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtSessionRegistry jwtSessionRegistry;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtSessionRegistry jwtSessionRegistry) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtSessionRegistry = jwtSessionRegistry;
    }

    /**
     * 회원가입 처리
     * 
     * @param signupRequest 회원가입 요청 정보
     * @return 생성된 사용자 정보
     */
    @Transactional
    public UserResponseDTO signup(SignupRequestDTO signupRequest) {
        // 중복 사용자명 검증
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + signupRequest.getUsername());
        }
        
        // 중복 이메일 검증
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + signupRequest.getEmail());
        }
        
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        
        // 사용자 생성 (기본 권한은 ROLE_USER)
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);
        
        return convertToDTO(savedUser);
    }

    /**
     * 사용자 권한 변경
     * 
     * @param userId 권한을 변경할 사용자 ID
     * @param newRole 새로운 권한
     * @return 업데이트된 사용자 정보
     */
	/* 설명. @PreAuthorize:
	 *  Spring Security의 보안 어노테이션으로, 메서드 실행 전에 권한 검사를 수행한다.
	 *  특정 권한이 있는 사용자만 해당 메서드를 호출할 수 있도록 제한한다.
	 *  이 어노테이션은 메서드 실행 전에 권한 검사를 수행하며, 권한이 없으면 예외가 발생한다.
	 *  이를 통해 관리자 권한이 있는 사용자만 사용자 권한을 변경할 수 있도록 보호한다.
	 * */
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public UserResponseDTO updateUserRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
        
        String username = user.getUsername();
        Role oldRole = user.getRole();
        
        System.out.println("[UserService] 사용자 권한 변경 시작");
        System.out.println("[UserService] - 사용자: " + username);
        System.out.println("[UserService] - 기존 권한: " + oldRole);
        System.out.println("[UserService] - 새 권한: " + newRole);
        
        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        
        // 권한이 변경된 사용자의 모든 토큰을 무효화 (JWT 강제 로그아웃)
        jwtSessionRegistry.revokeAllByUsername(username);
        
        System.out.println("[UserService] 사용자 권한 변경 완료 및 세션 무효화 처리됨");
        
        return convertToDTO(updatedUser);
    }

    /**
     * User 엔티티를 UserResponseDTO로 변환
     * <p>
     * 설명. 민감한 정보(비밀번호)를 제외하고 클라이언트에게 안전한 사용자 정보만 전달하기 위해
     * 엔티티를 DTO로 변환하는 유틸리티 메서드
     */
    private UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}