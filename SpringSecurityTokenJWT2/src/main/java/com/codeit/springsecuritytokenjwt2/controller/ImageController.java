package com.codeit.springsecuritytokenjwt2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 이미지 서빙 컨트롤러
 * 업로드된 이미지 파일을 클라이언트에 제공한다
 */
@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {
    
    @Value("${file.upload.path}")
    private String uploadPath;
    
    /**
     * 이미지 파일을 서빙한다
     * @param filename 이미지 파일명
     * @return 이미지 파일
     */
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        
		try {
            // 프로젝트 루트 기준으로 절대 경로 생성
            String projectRoot = System.getProperty("user.dir");
            String fullUploadPath = projectRoot + File.separator + uploadPath;
            Path filePath = Paths.get(fullUploadPath).resolve(filename);
            Resource resource = new FileSystemResource(filePath);
            
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            
            // 파일 확장자에 따른 Content-Type 설정
            String contentType = getContentType(filename);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
                    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 이미지 파일을 다운로드한다
     * @param filename 다운로드할 이미지 파일명
     * @return 다운로드 파일
     */
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) {
        try {
            // 프로젝트 루트 기준으로 절대 경로 생성
            String projectRoot = System.getProperty("user.dir");
            String fullUploadPath = projectRoot + File.separator + uploadPath;
            Path filePath = Paths.get(fullUploadPath).resolve(filename);
            Resource resource = new FileSystemResource(filePath);
            
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            
            // 파일 확장자에 따른 Content-Type 설정
            String contentType = getContentType(filename);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
                    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 파일 확장자에 따른 Content-Type을 반환한다
     * @param filename 파일명
     * @return Content-Type
     */
    private String getContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        
        return switch (extension) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG_VALUE;
            case "png" -> MediaType.IMAGE_PNG_VALUE;
            case "gif" -> MediaType.IMAGE_GIF_VALUE;
            default -> MediaType.APPLICATION_OCTET_STREAM_VALUE;
        };
    }
} 