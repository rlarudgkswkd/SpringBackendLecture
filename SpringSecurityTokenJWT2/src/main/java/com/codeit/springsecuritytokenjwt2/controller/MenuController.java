package com.codeit.springsecuritytokenjwt2.controller;

import com.codeit.springsecuritytokenjwt2.dto.MenuDTO;
import com.codeit.springsecuritytokenjwt2.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 메뉴 API 컨트롤러
 * 메뉴 관련 REST API를 제공한다
 */
@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuController {
    
    private final MenuService menuService;
    
    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }
    
    /**
     * 모든 주문 가능한 메뉴 목록을 조회한다
     * @return 메뉴 목록
     */
    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        try {
            List<MenuDTO> menus = menuService.findAllOrderableMenus();
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 메뉴 코드로 특정 메뉴를 조회한다
     * @param menuCode 메뉴 코드
     * @return 메뉴 정보
     */
    @GetMapping("/{menuCode}")
    public ResponseEntity<MenuDTO> getMenuByCode(@PathVariable Long menuCode) {
        try {
            MenuDTO menu = menuService.findMenuByCode(menuCode);
            return ResponseEntity.ok(menu);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 새로운 메뉴를 등록한다
     * @param menuName 메뉴명
     * @param menuPrice 메뉴 가격
     * @param menuDescription 메뉴 설명
     * @param categoryCode 카테고리 코드
     * @param menuStock 메뉴 재고
     * @param imageFile 메뉴 이미지 파일
     * @return 등록된 메뉴 정보
     */
    @PostMapping
    public ResponseEntity<MenuDTO> registerMenu(
            @RequestParam("menuName") String menuName,
            @RequestParam("menuPrice") Integer menuPrice,
            @RequestParam("menuDescription") String menuDescription,
            @RequestParam("categoryCode") Long categoryCode,
            @RequestParam("menuStock") Integer menuStock,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        
        try {
            MenuDTO registeredMenu = menuService.registerMenu(
                    menuName, menuPrice, menuDescription, 
                    categoryCode, menuStock, imageFile);
            return ResponseEntity.ok(registeredMenu);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 메뉴를 삭제한다
     * @param menuCode 삭제할 메뉴 코드
     * @return 삭제 결과
     */
    @DeleteMapping("/{menuCode}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuCode) {
        try {
            menuService.deleteMenu(menuCode);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
} 