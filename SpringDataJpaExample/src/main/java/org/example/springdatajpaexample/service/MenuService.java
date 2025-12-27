package org.example.springdatajpaexample.service;

import lombok.RequiredArgsConstructor;
import org.example.springdatajpaexample.domain.Menu;
import org.example.springdatajpaexample.dto.MenuResponse;
import org.example.springdatajpaexample.repository.MenuRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;

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
}
