package org.example.Head09_SpringMVC.topic09_ResponseDataProcess.example06;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ResponseEntityController {
    @PostMapping("/v1/members/response")
    public ResponseEntity<Map<String, String>> postMemberResponse(@RequestParam("email") String email,
                                                                  @RequestParam("name") String name,
                                                                  @RequestParam("phone") String phone) {
        Map<String, String> response = new HashMap<>();
        response.put("email", email);
        response.put("name", name);
        response.put("phone", phone);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
