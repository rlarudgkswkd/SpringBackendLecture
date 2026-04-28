package com.codeit.springsecuritytokenjwt2.service;

import com.codeit.springsecuritytokenjwt2.dto.CategoryDTO;
import com.codeit.springsecuritytokenjwt2.entity.Category;
import com.codeit.springsecuritytokenjwt2.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 카테고리 서비스
 * 카테고리 관련 비즈니스 로직을 처리한다
 */
@Service
@Transactional(readOnly = true)
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    /**
     * 모든 카테고리 목록을 조회한다
     * @return 카테고리 DTO 목록
     */
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 카테고리 코드로 카테고리를 조회한다
     * @param categoryCode 카테고리 코드
     * @return 카테고리 DTO
     */
    public CategoryDTO findCategoryByCode(Long categoryCode) {
        Category category = categoryRepository.findById(categoryCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다: " + categoryCode));
        return convertToDTO(category);
    }
    
    /**
     * Category 엔티티를 CategoryDTO로 변환한다
     * @param category 카테고리 엔티티
     * @return 카테고리 DTO
     */
    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(
                category.getCategoryCode(),
                category.getCategoryName()
        );
    }
    
    /**
     * CategoryDTO를 Category 엔티티로 변환한다
     * @param categoryDTO 카테고리 DTO
     * @return 카테고리 엔티티
     */
    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getCategoryName());
        category.setCategoryCode(categoryDTO.getCategoryCode());
        return category;
    }
} 