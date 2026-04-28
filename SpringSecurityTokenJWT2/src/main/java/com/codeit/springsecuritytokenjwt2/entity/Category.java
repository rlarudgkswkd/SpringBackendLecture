package com.codeit.springsecuritytokenjwt2.entity;

import jakarta.persistence.*;

/**
 * 카테고리 엔티티
 * 메뉴의 분류를 나타낸다
 */
@Entity
@Table(name = "tbl_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_code")
    private Long categoryCode;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    // 기본 생성자
    public Category() {}

    // 생성자
    public Category(String categoryName) {
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
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
} 