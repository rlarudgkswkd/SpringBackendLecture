package org.example.Head11_RestFulAPiDesignAndImplement.topic05_RestFulDocument.example01.controller;

import org.example.Head11_RestFulAPiDesignAndImplement.topic05_RestFulDocument.example01.dto.RestDocsMemberResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class RestDocsMemberController {

    @GetMapping("/{id}")
    public RestDocsMemberResponse getMember(@PathVariable Long id) {
        return new RestDocsMemberResponse(id, "kim", "kim@test.com");
    }
}