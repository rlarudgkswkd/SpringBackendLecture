package org.example.Head09_SpringMVC.topic09_ResponseDataProcess.example05;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ResponseBodyExample2Controller {
    @PostMapping("/v1/members/json")
    @ResponseBody
    public Map<String, String> postMemberJson(@RequestParam("email") String email,
                                              @RequestParam("name") String name,
                                              @RequestParam("phone") String phone) {
        Map<String, String> response = new HashMap<>();
        response.put("email", email);
        response.put("name", name);
        response.put("phone", phone);
        return response;
    }

}
