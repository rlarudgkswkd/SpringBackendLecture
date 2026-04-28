package com.codeit.springsecuritytokenjwt2.user.repository;

import com.codeit.springsecuritytokenjwt2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 리포지토리
 *
 * 사용자 엔티티에 대한 데이터 접근 계층으로 기본적인 CRUD 연산 외에 사용자 도메인에 특화된 쿼리 메서드들을 정의한다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 사용자명으로 사용자를 조회합니다.
     * Spring Security 인증에서 사용됩니다.
     *
     * @param username 사용자명 (로그인 ID)
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByUsername(String username);

    /**
     * 사용자명이 존재하는지 확인합니다.
     *
     * @param username 사용자명
     * @return 존재 여부
     */
    boolean existsByUsername(String username);

    /**
     * 이메일이 존재하는지 확인합니다.
     *
     * @param email 이메일 주소
     * @return 존재 여부
     */
    boolean existsByEmail(String email);

}