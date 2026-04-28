package com.codeit.springsecuritytokenjwt2.service;

import com.codeit.springsecuritytokenjwt2.dto.CategoryDTO;
import com.codeit.springsecuritytokenjwt2.dto.MenuDTO;
import com.codeit.springsecuritytokenjwt2.entity.Category;
import com.codeit.springsecuritytokenjwt2.entity.Menu;
import com.codeit.springsecuritytokenjwt2.repository.CategoryRepository;
import com.codeit.springsecuritytokenjwt2.repository.MenuRepository;
import com.codeit.springsecuritytokenjwt2.util.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 메뉴 서비스
 * 메뉴 관련 비즈니스 로직을 처리한다
 */
@Service
@Transactional(readOnly = true)
public class MenuService {
    
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploadUtils fileUploadUtils;
    
    @Autowired
    public MenuService(MenuRepository menuRepository, 
                       CategoryRepository categoryRepository,
                       FileUploadUtils fileUploadUtils) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.fileUploadUtils = fileUploadUtils;
    }
    
    /**
     * 모든 주문 가능한 메뉴 목록을 조회한다
     * @return 메뉴 DTO 목록
     */
    public List<MenuDTO> findAllOrderableMenus() {
        List<Menu> menus = menuRepository.findOrderableMenus();
        return menus.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 메뉴 코드로 메뉴를 조회한다
     * @param menuCode 메뉴 코드
     * @return 메뉴 DTO
     */
    public MenuDTO findMenuByCode(Long menuCode) {
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다: " + menuCode));
        return convertToDTO(menu);
    }
    
    /**
     * 새로운 메뉴를 등록한다
     * @param menuName 메뉴명
     * @param menuPrice 메뉴 가격
     * @param menuDescription 메뉴 설명
     * @param categoryCode 카테고리 코드
     * @param menuStock 메뉴 재고
     * @param imageFile 메뉴 이미지 파일
     * @return 등록된 메뉴 DTO
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public MenuDTO registerMenu(String menuName, Integer menuPrice, String menuDescription,
                               Long categoryCode, Integer menuStock, MultipartFile imageFile) {
        try {
            // 카테고리 조회
            Category category = categoryRepository.findById(categoryCode)
                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다: " + categoryCode));
            
            // 이미지 파일 업로드
            String imageUrl = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                if (!fileUploadUtils.isImageFile(imageFile)) {
                    throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
                }
                imageUrl = fileUploadUtils.uploadFile(imageFile);
            }
            
            // 메뉴 엔티티 생성 및 저장
            Menu menu = new Menu(menuName, menuPrice, menuDescription, category, imageUrl, menuStock);
            Menu savedMenu = menuRepository.save(menu);
            
            return convertToDTO(savedMenu);
            
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 메뉴를 삭제한다
     * @param menuCode 삭제할 메뉴 코드
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteMenu(Long menuCode) {
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다: " + menuCode));
        
        // 연관된 이미지 파일 삭제
        if (menu.getMenuImageUrl() != null && !menu.getMenuImageUrl().isEmpty()) {
            fileUploadUtils.deleteFile(menu.getMenuImageUrl());
        }
        
        // 메뉴 삭제
        menuRepository.delete(menu);
    }
    
    /**
     * Menu 엔티티를 MenuDTO로 변환한다
     * @param menu 메뉴 엔티티
     * @return 메뉴 DTO
     */
    private MenuDTO convertToDTO(Menu menu) {
        CategoryDTO categoryDTO = null;
        if (menu.getCategory() != null) {
            categoryDTO = new CategoryDTO(
                    menu.getCategory().getCategoryCode(),
                    menu.getCategory().getCategoryName()
            );
        }
        
        return new MenuDTO(
                menu.getMenuCode(),
                menu.getMenuName(),
                menu.getMenuPrice(),
                menu.getMenuDescription(),
                menu.getMenuOrderable(),
                categoryDTO,
                menu.getMenuImageUrl(),
                menu.getMenuStock()
        );
    }
} 