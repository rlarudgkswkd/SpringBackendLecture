package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.security;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
public class FileSecurityValidator {

    // 허용 확장자(예시)
    private static final Set<String> ALLOWED_EXT = Set.of("png", "jpg", "jpeg", "pdf", "txt");

    // 허용 Content-Type(예시)
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/png", "image/jpeg", "application/pdf", "text/plain"
    );

    // 최대 파일 크기 (예: 5MB)
    private static final long MAX_SIZE = 5 * 1024 * 1024;

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("파일 크기가 너무 큽니다. (최대 5MB)");
        }

        String original = file.getOriginalFilename();
        if (original == null) original = "unknown";

        String ext = extractExt(original);
        if (!ALLOWED_EXT.contains(ext)) {
            throw new IllegalArgumentException("허용되지 않은 확장자입니다: " + ext);
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("허용되지 않은 Content-Type입니다: " + contentType);
        }

        // ⚠️ 데모: 파일 내용 검사(실제 스캔은 하지 않음)
        // 실무에서는 안티바이러스/샌드박스/시그니처 검사 등을 수행
        System.out.println("[DEMO] 파일 내용 검사(악성코드/시그니처) 수행했다고 가정합니다. 실제 검사는 생략됨.");
    }

    private String extractExt(String filename) {
        int idx = filename.lastIndexOf('.');
        if (idx < 0 || idx == filename.length() - 1) return "";
        return filename.substring(idx + 1).toLowerCase();
    }
}