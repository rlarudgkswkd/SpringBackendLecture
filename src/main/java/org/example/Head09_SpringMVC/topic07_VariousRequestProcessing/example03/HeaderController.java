package org.example.Head09_SpringMVC.topic07_VariousRequestProcessing.example03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HeaderController {

    @GetMapping("/headers")
    @ResponseBody
    public String header(
            @RequestHeader("User-Agent") String userAgent
    ) {
        return "User-Agent=" + userAgent;
    }

    @GetMapping("/cookies")
    @ResponseBody
    public String cookie(
            @CookieValue(value = "SESSION", required = false) String session
    ) {
        return "SESSION=" + session;
    }
}

