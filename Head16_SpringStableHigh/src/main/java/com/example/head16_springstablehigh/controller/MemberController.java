package com.example.head16_springstablehigh.controller;

import com.example.head16_springstablehigh.entity.MemberTest;
import com.example.head16_springstablehigh.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    public MemberTest create(@RequestParam String name) {
        return memberRepository.save(new MemberTest(null, name));
    }

    @GetMapping
    public long count() {
        return memberRepository.count();
    }
}