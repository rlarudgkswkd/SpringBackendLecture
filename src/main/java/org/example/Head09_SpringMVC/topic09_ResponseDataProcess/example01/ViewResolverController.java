package org.example.Head09_SpringMVC.topic09_ResponseDataProcess.example01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewResolverController {
    @GetMapping("/v1/members/{id}")
    public String getMember(@PathVariable long id, Model model) {
        model.addAttribute("memberId", id);
        return "memberDetail"; // → templates/memberDetail.html
    }

}
