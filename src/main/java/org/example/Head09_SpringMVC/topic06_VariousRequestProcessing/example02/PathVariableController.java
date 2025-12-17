package org.example.Head09_SpringMVC.topic06_VariousRequestProcessing.example02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PathVariableController {

    @GetMapping("/users/{id}")
    @ResponseBody
    public String getUser(@PathVariable Long id) {
        return "userId=" + id;
    }
}