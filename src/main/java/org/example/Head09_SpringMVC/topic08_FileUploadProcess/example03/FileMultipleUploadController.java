package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class FileMultipleUploadController {
    @PostMapping("/upload/multiple")
    @ResponseBody
    public String uploadMultiple(
            @RequestParam List<MultipartFile> files
    ) {
        return "파일 개수: " + files.size();
    }
}
