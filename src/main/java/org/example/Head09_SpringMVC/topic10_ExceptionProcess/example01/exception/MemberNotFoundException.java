package org.example.Head09_SpringMVC.topic10_ExceptionProcess.example01.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long id) {
        super("회원이 존재하지 않습니다. id=" + id);
    }
}
