package com.codeit.springsecuritytokenjwt2.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 파일 업로드 유틸리티
 * build 폴더에 직접 파일을 저장하여 즉시 서빙이 가능하도록 한다
 */
@Component
public class FileUploadUtils {
    
    @Value("${file.upload.path}")
    private String uploadPath;
    
    /**
     * 파일을 업로드하고 저장된 파일명을 반환한다
     * @param file 업로드할 파일
     * @return 저장된 파일명
     * @throws IOException 파일 저장 실패시 발생
     */
    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }
        
        // 프로젝트 루트 기준으로 절대 경로 생성
        String projectRoot = System.getProperty("user.dir");
        String fullUploadPath = projectRoot + File.separator + uploadPath;
        
        // 업로드 디렉토리 생성
        File uploadDir = new File(fullUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // 고유한 파일명 생성
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        
        // 파일 저장
        Path filePath = Paths.get(fullUploadPath + File.separator + uniqueFilename);
        Files.write(filePath, file.getBytes());
        
        return uniqueFilename;
    }
    
    /**
     * 파일을 삭제한다
     * @param filename 삭제할 파일명
     * @return 삭제 성공 여부
     */
    public boolean deleteFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }
        
        try {
            // 프로젝트 루트 기준으로 절대 경로 생성
            String projectRoot = System.getProperty("user.dir");
            String fullUploadPath = projectRoot + File.separator + uploadPath;
            Path filePath = Paths.get(fullUploadPath + File.separator + filename);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 파일의 확장자를 추출한다
     * @param filename 파일명
     * @return 확장자 (점 포함)
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
    
    /**
     * 지원하는 이미지 파일인지 확인한다
     * @param file 확인할 파일
     * @return 이미지 파일 여부
     */
    public boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
} 