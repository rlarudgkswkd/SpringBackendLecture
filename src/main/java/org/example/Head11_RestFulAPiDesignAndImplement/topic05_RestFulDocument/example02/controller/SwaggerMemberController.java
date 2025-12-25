package org.example.Head11_RestFulAPiDesignAndImplement.topic05_RestFulDocument.example02.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.Head11_RestFulAPiDesignAndImplement.topic05_RestFulDocument.example02.dto.SwaggerMemberResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member API")
@RestController
@RequestMapping("/api/members")
public class SwaggerMemberController {

    @Operation(summary = "회원 조회")
    @GetMapping("/{id}")
    public SwaggerMemberResponse getMember(
            @Parameter(description = "회원 ID", example = "1")
            @PathVariable Long id) {
        return new SwaggerMemberResponse(id, "kim", "kim@test.com");
    }
}
