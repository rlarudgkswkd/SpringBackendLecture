package com.codeit.springsecuritytokenjwt2.auth.service;

import com.codeit.springsecuritytokenjwt2.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 사용자 세부 정보 구현체
 *
 * Spring Security의 UserDetails 인터페이스 구현체로 '로그인 인증' 및 '권한 검사' 등 모든 보안 로직의 '사용자 핵심 정보'를 보관하는 역할을 한다.
 * 여기서 핵심은 Security가 직접적으로 DB의 User 엔티티를 참조하지 않고, UserDetails라는 래퍼 객체로 감싼 후 인증을 수행한다.
 * 이제부터 인증(로그인) 성공 시, 해당 객체는 SecurityContext에 저장되고, 필요할 때마다 꺼내서 참조한다.
 */
public class CustomUserDetails implements UserDetails {

    /* 설명. UserDetails:
     *  Spring Security에서 인증(Authentication) 및 인가(Authorization) 로직의 '핵심 사용자 정보'를 추상화한 인터페이스다.
     *  시스템 내 '인증된 사용자'의 필수 정보(아이디, 비밀번호, 권한, 계정 상태 등)를 표준 방식으로 제공한다.
     *  또한 Security의 인증 과정(로그인 시 아이디/비밀번호 체크, 권한 검사 등)에서 UserDetails 구현체가 principal(주체)로 사용된다.
     *  인증 성공 시, SecurityContextHolder에 저장되는 principal 객체의 타입이 된다.
     *  (이 경우 위에서 설명했듯이 CustomUserDetails 타입으로 저장됨)
     *  인증이 성공하면 Spring Security는 인증된 사용자 정보를 담고 있는 Authentication 객체를 SpringContextHolder에 저장한다.
     *  (여기서 등장하는 SpringContextHolder가 인증된 사용자 정보를 보관하는 Thread-Local 저장소 역할을 수행한다.)
     *  이때, Authentication 객체의 principal 필드에 해당 구현체 타입의 객체가 저장된다.
     *  따라서 username, role과 같은 기본 정보는 UserDetails 인터페이스가 제공하는 표준 메서드를 사용하면 되고,
     *  기타 회원번호, 이메일, 주소, 성별 등의 추가 정보는 principal을 적절히 다운캐스팅해 접근할 수 있다. (아래 예시 참고)
     *  ----------------------------------------------------------------------------------------------------------------------------------
     *  예: 컨트롤러 레이어나 서비스 레이어에서 아래와 같이 접근할 수 있음.
     *  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     *  CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();
     *  String username = details.getUsername();    // UserDetails 표준 메서드
     *  Long userId = details.getUserId();          // CustomUserDetails 커스텀 메서드
     *  User user = details.getUser();              // 실제 엔티티에 접근
     *  ----------------------------------------------------------------------------------------------------------------------------------
     *  사실 단순히 생각했을 때 User 엔티티를 직접 써도 되는데, 왜 UserDetails라는 래퍼를 따로 사용할까?
     *  1. 보안성: User 엔티티에는 개인정보, 불필요한 필드(이메일, 전화번호, 가입일 등)가 포함되어 있을 수 있다.
     *             Security 로직에는 필요한 최소 정보(아이디, 비번, 권한, 상태 등)만 노출해야 한다.
     *  2. 도메인 분리: 애플리케이션의 비즈니스 도메인 모델(User)과 Security 인증 로직을 분리해 개발의 유연성과 유지보수성을 높인다.
     *  3. 확장성/유연성: 다양한 인증 방식(소셜, SSO, LDAP 등)과 연동할 때, 각 방식에 맞는 UserDetails 구현체를 따로 만들어 사용할 수 있다.
     *  ----------------------------------------------------------------------------------------------------------------------------------
     *  총 7개의 추상메서드를 가지고 있으며 모두 getter 메서드의 형태를 띄고 있다.
     *  get으로 시작하는 메서드 3개와 is로 시작하는 메서드 4개가 있는데 이들 중 get으로 시작하는 메서드 3개가 핵심 메서드다.
     * */

    /* 설명. (면접질문 대비용) Authentication 객체 vs. SecurityContextHolder vs. 세션
     *  인증이 성공하면 Authentication 객체가 생성되어 SecurityContext에 저장되고,
     *  SecurityContext는 SecurityContextHolder(보통 ThreadLocal)에 저장된다.
     *  웹 애플리케이션에서는 SecurityContextHolderFilter가 SecurityContext를 HttpSession과 연동해 자동으로 관리한다.
     *  즉, SecurityContextHolder는 "현재 인증 정보"의 저장소이지만, 반드시 세션과 1:1로 매핑되는 것은 아니다.
     *  (주로 "SecurityContextHolder가 세션과 동일하다"는 뉘앙스로 대답해서 틀림)
     *  ------------------------------------------------------------------------------------------------------------------
     *  Q) “SecurityContextHolder와 세션이 1:1 관계인가?”
     *  A) 아니다. SecurityContextHolder는 ThreadLocal(스레드), 세션은 HTTP 세션이므로 1:1로 매핑되지 않는다.
     *     다만, Spring Security가 SecurityContextHolderFilter를 통해 요청마다 두 정보를 연동해준다.
     * */

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    /* 설명. 사용자의 권한 정보를 List로 반환하는 메서드.
     *  UsernamePasswordAuthenticationToken에 사용자의 권한 정보를 반환할 때 사용된다.
     *  Security는 사용자의 역할/권한을 체크할 때 이 정보를 사용하며
     *  보통 "ROLE_"과 같은 접두사가 붙은 문자열을 반환해야 인가 로직이 정상 동작한다.
     *  만약 사용자가 여러개의 권한을 가질 수 있는 구조일 경우, List.of()에 여러 개의 SimpleGrantedAuthority를 추가하면 된다.
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        System.out.println("[CustomUserDetails] getAuthorites 실행됨...");

        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    /* 설명. 사용자의 비밀번호를 반환하는 메서드.
     *  UsernamePasswordAuthenticationToken과 사용자의 비밀번호를 비교할 때 사용된다.
     *  Spring Security의 내부 인증 과정(PasswordEncoder와 비교)에서만 사용된다.
     *  Controller 및 Service 레이어에서는 보통 getPassword() 사용을 피해야 하며, 보안상 노출하면 안된다.
     *  (해당 메서드가 언제 사용되는지 궁금하다면 아래 return 문 이전에 sout이나 로그를 찍어 확인해볼 수 있다)
     * */
    @Override
    public String getPassword() {

        System.out.println("[CustomUserDetails] getPassword 실행됨...");

        return user.getPassword();
    }

    /* 설명. 사용자의 아이디를 반환하는 메서드.
     *  UsernamePasswordAuthenticationToken과 사용자의 아이디를 비교할 때 사용된다.
     *  보통 username/email 등을 사용하며, 인증 시 principal 값으로 사용된다.
     *  이때, 사용자의 고유 아이디(Primary Key)가 아니라 로그인용 username임에 주의
     * */
    @Override
    public String getUsername() {

        System.out.println("[CustomUserDetails] getUsername 실행됨...");

        return user.getUsername();
    }

    /* 설명. 계정 만료 여부를 표현하는 메서드로,
     *  만약 false를 리턴하면 Security가 '계정 만료(expire)'로 간주해 인증 불가로 처리한다.
     *  주로 계정 유효기간 정책(예: 가입 후 1년 이상 미접속 시 만료 등)을 구현할 때 활용한다.
     *  일반적으로 true를 반환(기본값)하지만, 실무에서는 user.getExpiredAt() 등의 값과 비교해서 동적으로 반환하기도 한다.
     * */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* 설명. 잠겨있는 계정을 확인하는 메서드로,
     *  만약 false를 리턴하면 Security가 '계정 잠김'으로 간주해 인증 불가로 처리한다.
     *  주로 비밀번호 반복 실패로 일시적인 계정 lock, 오랜 기간 비접속으로 휴면 처리, 관리자의 블랙리스트 추가 등에 활용한다.
     *  해당 메서드 또한 user 엔티티에 lock 필드가 있을 경우 해당 값에 따라 동적으로 처리 가능.
     * */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* 설명. 인증 자격증명(credential) 만료 여부를 표현하는 메서드로,
     *  만약 false를 리턴하면 Security가 '자격증명 만료'로 간주해 인증 불가로 처리한다.
     *  (디폴트는 true)
     *  주로 90일 이상 비밀번호 미변경 시, 비밀번호 변경을 강제하는 정책 등에 활용할 수 있다.
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* 설명. 계정 비활성화 여부로 사용자가 사용할 수 없는 상태를 나타내며,
     *  만약 false를 리턴하면 Security가 '계정 비활성화'로 간주해 인증 불가로 처리한다.
     *  (디폴트는 true)
     *  실무에서는 user.getStatus() == ACTIVE 등으로 조건을 둘 수 있으며,
     *  주로 회원 탈퇴, 이메일 인증 미완료, 관리자가 비활성화 등에 활용할 수 있다.
     * */
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getter & Setter (지금같은 경우에는 굳이 만들지 않아도 됨)
    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return user.getUserId();
    }

	/* 설명. 사용자 정보 비교 메서드로,
	 *  equals()와 hashCode() 메서드를 재정의하여 사용자 정보 비교 시 사용된다.
	 *  주로 세션 관리 시 사용되며, 세션 관리 시 사용자 정보 비교 시 사용된다.
	 *  (https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html#ns-concurrent-sessions)
	 * */
    @Override
    public boolean equals(Object o) {
        // 자기 자신과 비교
		if (this == o) return true;
        // 타입 비교
        if (!(o instanceof CustomUserDetails that)) return false;
        // 사용자 이름 비교
        return Objects.equals(user.getUsername(), that.user.getUsername());
    }

    @Override
    public int hashCode() {
		// 사용자 이름 해시 코드 반환
        return Objects.hashCode(user.getUsername());
    }
}