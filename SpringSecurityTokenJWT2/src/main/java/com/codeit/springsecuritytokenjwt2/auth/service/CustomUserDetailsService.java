package com.codeit.springsecuritytokenjwt2.auth.service;

import com.codeit.springsecuritytokenjwt2.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/* 설명. UserDetailsService:
 *  Spring Security에서 사용자의 아이디를 인증하기 위한 인터페이스다.
 *  해당 인터페이스를 구현한 구현체는 loadUserByUsername() 메서드를 필수로 구현해야 하며,
 *  이 메서드가 반환한 UserDetails 객체가 Spring Security의 인증(로그인) 과정에서 principal로 사용된다.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /* 설명. UserDetailsService:
     *  Spring Security의 인증(authentication) 로직에서 UserDetails와 더불어 핵심 인터페이스로 동작한다.
     *  로그인 시 사용자가 입력한 ID(username)을 기반으로 DB 등에서 사용자를 찾아 UserDetails 타입의 객체로 변환해 반환하는 책임을 진다.
     *  (참고: 여기서 username은 사용자의 이름이 아닌 ID를 의미한다. lastName, firstName 등과 헷갈릴 수 있으니 주의하자.)
     * */

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	/* 설명. Spring Security의 인증 과정에서 자동으로 호출되는 메서드로,
	 *  사용자 ID(username)로 사용자 정보를 조회하여 UserDetails 객체로 반환한다.
	 * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("[CustomUserDetailsService] loadUserByUsername 호출됨...");

        return userRepository.findByUsername(username)
				// 사용자 정보를 조회하여 UserDetails 객체로 반환
                .map(user -> new CustomUserDetails(user))
				// 사용자 정보를 조회하여 UserDetails 객체로 반환
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }
}