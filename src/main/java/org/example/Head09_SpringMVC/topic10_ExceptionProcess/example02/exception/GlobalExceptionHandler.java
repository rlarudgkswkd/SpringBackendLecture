package org.example.Head09_SpringMVC.topic10_ExceptionProcess.example02.exception;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e,
                                        HttpServletRequest request,
                                        Model model) {

        model.addAttribute("status", 400);
        model.addAttribute("error", "잘못된 요청입니다.");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("path", request.getRequestURI());

        return "error/custom-error";
    }

    @ExceptionHandler(GlobalMemberNotFoundException.class)
    public String handleMemberNotFound(GlobalMemberNotFoundException e,
                                       HttpServletRequest request,
                                       Model model) {

        model.addAttribute("status", 404);
        model.addAttribute("error", "리소스를 찾을 수 없습니다.");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("path", request.getRequestURI());

        return "error/custom-error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e,
                                  HttpServletRequest request,
                                  Model model) {

        model.addAttribute("status", 500);
        model.addAttribute("error", "내부 서버 오류");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("path", request.getRequestURI());

        return "error/custom-error";
    }
}