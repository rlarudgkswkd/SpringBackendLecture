package org.example.Head09_SpringMVC.topic04_APItest.example01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        return Map.of(
                "message", "hello",
                "server", "spring-boot"
        );
    }
}
