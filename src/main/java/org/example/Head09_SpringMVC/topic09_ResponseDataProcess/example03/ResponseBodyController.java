package org.example.Head09_SpringMVC.topic09_ResponseDataProcess.example03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ResponseBodyController {
    @GetMapping("/v1/coffees/info")
    @ResponseBody
    public Map<String, Object> getCoffeeInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "콜드브루");
        map.put("price", 4000);
        return map; // JSON 응답으로 처리됨
    }
}
