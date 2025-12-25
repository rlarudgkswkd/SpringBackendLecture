package org.example.springdatajpaexample.controller;

import org.example.springdatajpaexample.domain.Menu;
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
    public Menu get(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<Menu> search(@RequestParam String keyword) {
        return service.search(keyword);
    }
}

