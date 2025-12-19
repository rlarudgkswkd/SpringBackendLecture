package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.service;

import org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.dto.UploadResult;
import org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.security.FileSecurityValidator;
import org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.util.UploadPathPolicy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {

    private final FileSecurityValidator validator;
    private final LocalS3Storage storage;

    public FileUploadService(FileSecurityValidator validator, LocalS3Storage storage) {
        this.validator = validator;
        this.storage = storage;
    }

    // ✅ 단일 업로드 공통 처리
    public UploadResult uploadOne(String purpose, MultipartFile file) throws IOException {
        validator.validate(file);

        String safeOriginal = sanitizeOriginalName(file.getOriginalFilename());
        String savedName = UUID.randomUUID() + "_" + safeOriginal;

        // 실무 스타일 난독화 경로 생성
        String prefix = UploadPathPolicy.generateObscuredPrefix(purpose);

        // "S3 Key"처럼 보이는 경로
        String s3Key = prefix + "/" + savedName;

        Path savedPath = storage.saveToLocalAsIfS3(file, s3Key);

        return new UploadResult(
                safeOriginal,
                savedName,
                savedPath.toString(),
                "s3://" + storage.getBucketName() + "/" + s3Key,
                file.getSize()
        );
    }

    // ✅ 다중 업로드 공통 처리
    public List<UploadResult> uploadMany(String purpose, List<MultipartFile> files) throws IOException {
        List<UploadResult> results = new ArrayList<>();
        if (files == null || files.isEmpty()) {
            return results;
        }

        for (MultipartFile f : files) {
            // 다중도 결국 단일 로직을 “재사용”
            results.add(uploadOne(purpose, f));
        }
        return results;
    }

    private String sanitizeOriginalName(String original) {
        if (original == null) original = "unknown";

        // 파일명에 경로가 섞여 들어오는 케이스 방지
        original = original.replace("\\", "_").replace("/", "_");

        // 아주 단순한 위험 문자 제거(데모)
        // 실무에서는 정규식으로 허용 문자만 남기는 방식이 더 안전하다.
        original = original.replace("..", "_");

        return original;
    }
}