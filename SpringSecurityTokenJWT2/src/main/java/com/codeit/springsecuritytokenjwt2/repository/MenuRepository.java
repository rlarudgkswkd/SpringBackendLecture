package com.codeit.springsecuritytokenjwt2.repository;

import com.codeit.springsecuritytokenjwt2.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메뉴 리포지토리
 * 메뉴 데이터 접근을 담당한다
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    
    /**
     * 주문 가능한 메뉴 목록을 최신순으로 조회한다
     * @return 주문 가능한 메뉴 목록 (최신순)
     */
    @Query("SELECT m FROM Menu m WHERE m.menuOrderable = 'Y' ORDER BY m.menuCode DESC")
    List<Menu> findOrderableMenus();
    
    /**
     * 카테고리별 메뉴 목록을 조회한다
     * @param categoryCode 카테고리 코드
     * @return 해당 카테고리의 메뉴 목록
     */
    @Query("SELECT m FROM Menu m WHERE m.category.categoryCode = :categoryCode AND m.menuOrderable = 'Y' ORDER BY m.menuCode")
    List<Menu> findMenusByCategoryCode(Long categoryCode);
} 