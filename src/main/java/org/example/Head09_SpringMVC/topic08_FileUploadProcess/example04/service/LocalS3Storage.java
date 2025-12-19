package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class LocalS3Storage {

    private final Path rootUploadDir;

    public LocalS3Storage() {
        // 프로젝트 실행 위치 기준 uploads 폴더 고정
        this.rootUploadDir = Path.of(System.getProperty("user.dir"), "uploads").toAbsolutePath();
    }

    public Path ensureRootDir() throws IOException {
        Files.createDirectories(rootUploadDir);
        return rootUploadDir;
    }

    /**
     * "S3에 저장한다"는 컨셉이지만 실제 저장은 로컬로 한다.
     * @param s3KeyLikePath 예: profile/ab12.../cd34.../uuid_filename.png
     */
    public Path saveToLocalAsIfS3(MultipartFile file, String s3KeyLikePath) throws IOException {
        ensureRootDir();

        // uploads/{s3KeyLikePath} 형태로 저장
        Path dest = rootUploadDir.resolve(s3KeyLikePath);

        // 중간 디렉터리 생성 (profile/xxxx/xxxx)
        Files.createDirectories(dest.getParent());

        file.transferTo(dest.toFile());
        return dest;
    }

    public String getBucketName() {
        // 데모용
        return "demo-bucket";
    }
}