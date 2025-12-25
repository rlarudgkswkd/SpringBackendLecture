package org.example.Head09_SpringMVC.topic10_ExceptionProcess.example01.controller;

import org.example.Head09_SpringMVC.topic10_ExceptionProcess.example01.exception.CustomResponse;
import org.example.Head09_SpringMVC.topic10_ExceptionProcess.example01.exception.MemberNotFoundException;
import org.example.Head09_SpringMVC.topic10_ExceptionProcess.example01.service.ExceptionMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class ExceptionMemberController {

    private final ExceptionMemberService memberService;

    public ExceptionMemberController(ExceptionMemberService memberService) {
        this.memberService = memberService;
    }

    // ✅ 정상 응답도 CustomResponse 사용
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CustomResponse<String>> getMember(@PathVariable Long id) {
        String result = memberService.findMember(id);

        return ResponseEntity.ok(
                CustomResponse.success(result)
        );
    }

    // 회원 없음 예외 처리
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<CustomResponse<Void>> handleMemberNotFound(MemberNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(CustomResponse.error("MEMBER_NOT_FOUND", e.getMessage()));
    }

    // 잘못된 요청 값 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse<Void>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.error("INVALID_REQUEST", e.getMessage()));
    }
}