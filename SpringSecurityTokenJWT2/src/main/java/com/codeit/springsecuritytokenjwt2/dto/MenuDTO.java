package com.codeit.springsecuritytokenjwt2.dto;

/**
 * 메뉴 데이터 전송 객체
 * 클라이언트와 서버 간 메뉴 정보 전송에 사용한다
 */
public class MenuDTO {
    
    private Long menuCode;
    private String menuName;
    private Integer menuPrice;
    private String menuDescription;
    private String menuOrderable;
    private CategoryDTO category;
    private String menuImageUrl;
    private Integer menuStock;
    
    // 기본 생성자
    public MenuDTO() {}
    
    // 생성자
    public MenuDTO(Long menuCode, String menuName, Integer menuPrice, 
                   String menuDescription, String menuOrderable, 
                   CategoryDTO category, String menuImageUrl, Integer menuStock) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
        this.menuOrderable = menuOrderable;
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
    
    public CategoryDTO getCategory() {
        return category;
    }
    
    public void setCategory(CategoryDTO category) {
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
        return "MenuDTO{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", menuDescription='" + menuDescription + '\'' +
                ", menuOrderable='" + menuOrderable + '\'' +
                ", category=" + category +
                ", menuImageUrl='" + menuImageUrl + '\'' +
                ", menuStock=" + menuStock +
                '}';
    }
} 