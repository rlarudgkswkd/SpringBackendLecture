package com.codeit.springsecuritytokenjwt2.dto;

/**
 * 카테고리 데이터 전송 객체
 * 클라이언트와 서버 간 카테고리 정보 전송에 사용한다
 */
public class CategoryDTO {
    
    private Long categoryCode;
    private String categoryName;
    
    // 기본 생성자
    public CategoryDTO() {}
    
    // 생성자
    public CategoryDTO(Long categoryCode, String categoryName) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }
    
    // Getter 및 Setter
    public Long getCategoryCode() {
        return categoryCode;
    }
    
    public void setCategoryCode(Long categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
} 