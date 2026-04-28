package com.codeit.springsecuritytokenjwt2.entity;

import jakarta.persistence.*;

/**
 * 메뉴 엔티티
 * 레스토랑의 메뉴 정보를 나타낸다
 */
@Entity
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private Long menuCode;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private Integer menuPrice;

    @Column(name = "menu_description")
    private String menuDescription;

    @Column(name = "menu_orderable", nullable = false, length = 1)
    private String menuOrderable = "Y";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private Category category;

    @Column(name = "menu_image_url")
    private String menuImageUrl;

    @Column(name = "menu_stock", nullable = false)
    private Integer menuStock = 0;

    // 기본 생성자
    public Menu() {}

    // 생성자
    public Menu(String menuName, Integer menuPrice, String menuDescription, 
                Category category, String menuImageUrl, Integer menuStock) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
        this.category = category;
        this.menuImageUrl = menuImageUrl;
        this.menuStock = menuStock;
    }

    // Getter 및 Setter
    public Long getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Long menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(Integer menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public String getMenuOrderable() {
        return menuOrderable;
    }

    public void setMenuOrderable(String menuOrderable) {
        this.menuOrderable = menuOrderable;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getMenuImageUrl() {
        return menuImageUrl;
    }

    public void setMenuImageUrl(String menuImageUrl) {
        this.menuImageUrl = menuImageUrl;
    }

    public Integer getMenuStock() {
        return menuStock;
    }

    public void setMenuStock(Integer menuStock) {
        this.menuStock = menuStock;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", menuDescription='" + menuDescription + '\'' +
                ", menuOrderable='" + menuOrderable + '\'' +
                ", category=" + (category != null ? category.getCategoryName() : null) +
                ", menuImageUrl='" + menuImageUrl + '\'' +
                ", menuStock=" + menuStock +
                '}';
    }
} 