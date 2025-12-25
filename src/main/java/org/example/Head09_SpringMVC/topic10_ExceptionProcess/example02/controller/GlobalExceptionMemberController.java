package org.example.Head09_SpringMVC.topic10_ExceptionProcess.example02.controller;

import org.example.Head09_SpringMVC.topic10_ExceptionProcess.example02.exception.GlobalMemberNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GlobalExceptionMemberController {

    @GetMapping("/")
    public String home() {
        return "home"; // templates/home.html
    }

    @GetMapping("/members/{id}")
    public String memberDetail(@PathVariable Long id, Model model) {

        // 1) 잘못된 요청 값 -> 400 페이지
        if (id <= 0) {
            throw new IllegalArgumentException("id는 1 이상의 값이어야 합니다.");
        }

        // 2) 존재하지 않는 회원 -> 404 페이지
        if (id >= 100) {
            throw new GlobalMemberNotFoundException(id);
        }

        // 3) 정상 흐름 -> 화면 렌더링
        model.addAttribute("memberId", id);
        model.addAttribute("name", "홍길동");
        return "home"; // 데모 단순화: home 화면에 값만 올려도 됨
    }
}