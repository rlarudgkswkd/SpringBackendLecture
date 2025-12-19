package org.example.Head09_SpringMVC.topic09_ResponseDataProcess.example04;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResponseModelController {
    @PostMapping("/v1/members")
    public String postMember(@RequestParam("email") String email,
                             @RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             Model model) {
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);
        return "memberResult"; // templates/memberResult.html
    }
}
