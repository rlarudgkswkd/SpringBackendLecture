package org.example.springdatajpaexample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springdatajpaexample.domain.Category;
import org.example.springdatajpaexample.domain.Menu;
import org.example.springdatajpaexample.dto.MenuResponse;
import org.example.springdatajpaexample.repository.CategoryRepository;
import org.example.springdatajpaexample.repository.MenuRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static jakarta.transaction.Status.STATUS_COMMITTED;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;
    private final CategoryRepository categoryRepository;
    private final AuditService auditService; // 추가

    @Transactional(readOnly = true)
    public MenuResponse findById(Long id) {
        Menu menu = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메뉴 없음"));

        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getCategory().getName()
        );
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> search(String keyword) {
        return repository.findByNameContainingWithCategory(keyword).stream()
                .map(m -> new MenuResponse(m.getId(), m.getName(), m.getPrice(), m.getCategory().getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> searchEntityGraph(String keyword) {
        return repository.findByNameContaining(keyword).stream()
                .map(m -> new MenuResponse(m.getId(), m.getName(), m.getPrice(), m.getCategory().getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findByCategory(Long categoryId) {
        return repository.findByCategoryId(categoryId).stream()
                .map(m -> new MenuResponse(m.getId(), m.getName(), m.getPrice(), m.getCategory().getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findExpensiveMenusInCategory(String categoryName, int minPrice) {
        return repository.findByCategoryNameAndMinPrice(categoryName, minPrice).stream()
                .map(m -> new MenuResponse(m.getId(), m.getName(), m.getPrice(), m.getCategory().getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findExpensiveMenusInCategory2(String categoryName, int minPrice) {
        return repository.findByCategoryNameAndPriceGreaterThanEqualOrderByPriceDesc(categoryName, minPrice)
                .stream()
                .map(m -> new MenuResponse(
                        m.getId(),
                        m.getName(),
                        m.getPrice(),
                        m.getCategory().getName()
                ))
                .toList();
    }

    // [추가] Page + Sort 활용 예제  (Menu -> MenuResponse)
    @Transactional(readOnly = true)
    public Page<MenuResponse> findMenusPageByCategoryAndMinPrice(
            String categoryName,
            int minPrice,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        return repository.findByCategoryNameAndPriceGreaterThanEqual(categoryName, minPrice, pageable)
                .map(m -> new MenuResponse(m.getId(), m.getName(), m.getPrice(), m.getCategory().getName()));
    }

    // [추가] Pageable 그대로 받는 예제  (Menu -> MenuResponse)
    @Transactional(readOnly = true)
    public Page<MenuResponse> findMenusPageByCategoryAndMinPrice(
            String categoryName,
            int minPrice,
            Pageable pageable
    ) {
        return repository.findByCategoryNameAndPriceGreaterThanEqual(categoryName, minPrice, pageable)
                .map(m -> new MenuResponse(m.getId(), m.getName(), m.getPrice(), m.getCategory().getName()));
    }

    // [추가] Slice 활용 예제(무한 스크롤)  (Menu -> MenuResponse)
    @Transactional(readOnly = true)
    public Slice<MenuResponse> findMenusSliceByCategoryAndMinPrice(
            String categoryName,
            int minPrice,
            Pageable pageable
    ) {
        // ✅ 여기만 "findSliceBy..."로 바꿔야 메서드 충돌이 안 남(이전 대화 내용 반영)
        return repository.findSliceByCategoryNameAndPriceGreaterThanEqual(categoryName, minPrice, pageable)
                .map(m -> new MenuResponse(m.getId(), m.getName(), m.getPrice(), m.getCategory().getName()));
    }

    @Transactional(readOnly = true)
    public Page<MenuResponse> searchMenus(
            Integer minPrice,
            String categoryName,
            Pageable pageable
    ) {
        return repository
//                .findByMinPriceAndOptionalCategory(minPrice, categoryName, pageable)
                .findByMinPriceAndOptionalCategoryWithCategory(minPrice, categoryName, pageable)
                .map(m -> new MenuResponse(
                        m.getId(),
                        m.getName(),
                        m.getPrice(),
                        m.getCategory().getName() // ⚠️ LAZY → N+1 (다음 챕터에서 해결)
                ));
    }

    // 실습 A: 성공하면 커밋
    @Transactional
    public void txIncrease(String categoryName, int delta) {
        List<Menu> menus = repository.findByCategoryName(categoryName);
        menus.forEach(m -> m.increasePrice(delta));
        // save 호출 없어도 dirty checking으로 UPDATE 됨
    }

    // 실습 B: 중간에 예외 → 전체 롤백(신규 메뉴 insert + 가격 update 전부)
    @Transactional
    public void txCreateAndIncreaseWithRollback(
            String categoryName,
            String newMenuName,
            int newMenuPrice,
            int delta
    ) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

        // 1) 신규 메뉴 insert
        repository.save(new Menu(newMenuName, newMenuPrice, category));

        // 2) 가격 일괄 인상(update)
        List<Menu> menus = repository.findByCategoryName(categoryName);
        menus.forEach(m -> m.increasePrice(delta));

        // 3) 강제 예외 → 롤백 확인
        throw new RuntimeException("강제 예외(롤백 확인)");
    }

    @Transactional
    public void changePriceWithAuditAndFail(Long menuId, int newPrice) {
        log.info("[Outer] tx active={}", TransactionSynchronizationManager.isActualTransactionActive());

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override public void afterCompletion(int status) {
                log.info("[Outer] END = {}", status == STATUS_COMMITTED ? "COMMIT" : "ROLLBACK");
            }
        });

        // 1) 바깥 트랜잭션에서 가격 변경
        repository.updatePrice(menuId, newPrice);

        // 2) 감사 기록(항상 새 트랜잭션)
        auditService.writeAuditMenu(1L);

        // 3) 일부러 실패시켜 바깥 트랜잭션 롤백 유도
        throw new RuntimeException("OUTER FAIL");
    }
}
