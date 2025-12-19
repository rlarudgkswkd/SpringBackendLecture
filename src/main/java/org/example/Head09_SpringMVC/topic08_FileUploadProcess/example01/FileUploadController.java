package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @PostMapping("/upload")
    @ResponseBody
    public String upload(
            @RequestParam String title,
            @RequestParam MultipartFile file
    ) {
        return "title=" + title +
                ", filename=" + file.getOriginalFilename() +
                ", size=" + file.getSize();
    }
}