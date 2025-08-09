package org.example.springbootjardemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootJarDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJarDemoApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello, Executable JAR!";
    }

}
