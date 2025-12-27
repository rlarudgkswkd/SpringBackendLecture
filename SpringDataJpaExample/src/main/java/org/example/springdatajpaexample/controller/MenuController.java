package org.example.springdatajpaexample.controller;

import org.example.springdatajpaexample.domain.Menu;
import org.example.springdatajpaexample.dto.MenuResponse;
import org.example.springdatajpaexample.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public MenuResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<MenuResponse> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<MenuResponse> byCategory(@PathVariable Long categoryId) {
        return service.findByCategory(categoryId);
    }

    @GetMapping("/filter")
    public List<MenuResponse> filter(
            @RequestParam String categoryName,
            @RequestParam int minPrice
    ) {
        return service.findExpensiveMenusInCategory(categoryName, minPrice);
    }

    @GetMapping("/filter2")
    public List<MenuResponse> filter2(
            @RequestParam String categoryName,
            @RequestParam int minPrice
    ) {
        return service.findExpensiveMenusInCategory2(categoryName, minPrice);
    }
}