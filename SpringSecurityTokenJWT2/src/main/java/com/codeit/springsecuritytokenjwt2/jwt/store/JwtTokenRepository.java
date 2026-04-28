package com.codeit.springsecuritytokenjwt2.jwt.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/* 설명. JWT 토큰 메타데이터를 저장/조회하는 JPA 레포지토리 */
@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, String> {

	/* 설명. 특정 사용자명의 모든 토큰을 조회한다. */
    List<JwtTokenEntity> findByUsername(String username);
}


