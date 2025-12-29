package org.example.springdatajpaexample.controller;

import lombok.RequiredArgsConstructor;
import org.example.springdatajpaexample.exception.MenuCheckedException;
import org.example.springdatajpaexample.service.MenuTxService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menus/tx/rollback")
@RequiredArgsConstructor
public class MenuTxController {

    private final MenuTxService menuTxService;

    @PostMapping("/runtime")
    public String runtime(@RequestParam Long menuId, @RequestParam int price) {
        menuTxService.updateThenRuntimeRollback(menuId, price);
        return "OK";
    }

    @PostMapping("/checked-commit")
    public String checkedCommit(@RequestParam Long menuId, @RequestParam int price) throws MenuCheckedException {
        menuTxService.updateThenCheckedCommit(menuId, price);
        return "OK";
    }

    @PostMapping("/checked-rollback")
    public String checkedRollback(@RequestParam Long menuId, @RequestParam int price) throws MenuCheckedException {
        menuTxService.updateThenCheckedRollback(menuId, price);
        return "OK";
    }

    @PostMapping("/no-rollback-for")
    public String noRollbackFor(@RequestParam Long menuId, @RequestParam int price) {
        menuTxService.updateThenNoRollbackFor(menuId, price);
        return "OK";
    }

    @PostMapping("/swallow-commit")
    public String swallowCommit(@RequestParam Long menuId, @RequestParam int price) {
        menuTxService.updateThenSwallowExceptionCommit(menuId, price);
        return "OK";
    }

    @PostMapping("/swallow-rollback")
    public String swallowRollback(@RequestParam Long menuId, @RequestParam int price) {
        menuTxService.updateThenSwallowButRollback(menuId, price);
        return "OK";
    }
}