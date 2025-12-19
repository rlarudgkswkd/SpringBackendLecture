package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.controller;

import org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.dto.UploadResult;
import org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.service.FileUploadService;
import org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.util.UploadPathPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class DemoFileUploadController {

    private final FileUploadService uploadService;

    public DemoFileUploadController(FileUploadService uploadService) {
        this.uploadService = uploadService;
    }

    // ✅ 단일 파일 업로드 라우팅
    @PostMapping("/upload/profile")
    @ResponseBody
    public UploadResult uploadProfile(@RequestParam("file") MultipartFile file) throws IOException {
        // Controller는 "어디로 업로드할지(purpose)"만 결정하고 서비스 호출
        return uploadService.uploadOne(UploadPathPolicy.PROFILE, file);
    }

    // ✅ 다중 파일 업로드 라우팅
    // form-data key를 files로 보내면 List로 바인딩됨
    @PostMapping("/upload/attachments")
    @ResponseBody
    public List<UploadResult> uploadAttachments(@RequestParam("files") List<MultipartFile> files) throws IOException {
        return uploadService.uploadMany(UploadPathPolicy.ATTACHMENTS, files);
    }
}