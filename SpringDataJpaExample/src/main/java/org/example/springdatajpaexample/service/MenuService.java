package org.example.springdatajpaexample.service;

import org.example.springdatajpaexample.domain.Menu;
import org.example.springdatajpaexample.dto.MenuResponse;
import org.example.springdatajpaexample.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

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
}
