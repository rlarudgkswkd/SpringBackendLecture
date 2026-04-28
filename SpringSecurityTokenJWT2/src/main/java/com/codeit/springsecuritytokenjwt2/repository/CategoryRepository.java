package com.codeit.springsecuritytokenjwt2.repository;

import com.codeit.springsecuritytokenjwt2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 카테고리 리포지토리
 * 카테고리 데이터 접근을 담당한다
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * 카테고리명으로 카테고리를 검색한다
     * @param categoryName 카테고리명
     * @return 카테고리 엔티티
     */
    Category findByCategoryName(String categoryName);
} 