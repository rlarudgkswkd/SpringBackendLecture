package org.example.Head09_SpringMVC.topic10_ExceptionProcess.example01.service;

import org.example.Head09_SpringMVC.topic10_ExceptionProcess.example01.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionMemberService {

    public String findMember(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id는 1 이상의 값이어야 합니다.");
        }

        if (id != 1L) {
            throw new MemberNotFoundException(id);
        }

        return "member id = " + id;
    }
}
