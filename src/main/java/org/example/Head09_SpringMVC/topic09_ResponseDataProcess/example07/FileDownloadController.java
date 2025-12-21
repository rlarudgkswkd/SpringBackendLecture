package org.example.Head09_SpringMVC.topic09_ResponseDataProcess.example07;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class FileDownloadController {

    @GetMapping("/v1/files/download-txt")
    public ResponseEntity<Resource> downloadTxt() throws IOException {
        return download(FileSetupRunner.SAMPLE_TXT, MediaType.TEXT_PLAIN);
    }

    @GetMapping("/v1/files/download-korean")
    public ResponseEntity<Resource> downloadKorean() throws IOException {
        return download(FileSetupRunner.SAMPLE_KO, MediaType.TEXT_PLAIN);
    }

    private ResponseEntity<Resource> download(Path file, MediaType type) throws IOException {
        if (Files.notExists(file)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new InputStreamResource(Files.newInputStream(file));

        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(file.getFileName().toString())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .contentType(type)
                .contentLength(Files.size(file))
                .body(resource);
    }
}