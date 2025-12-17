package org.example.Head09_SpringMVC.topic06_VariousRequestProcessing.example01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RequestParamController {

    @GetMapping("/search")
    @ResponseBody
    public String search(
            @RequestParam String keyword,
            @RequestParam int page
    ) {
        return "keyword=" + keyword + ", page=" + page;
    }
}