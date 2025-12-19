package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * 파일 업로드 및 로컬 저장을 담당하는 Controller
 *
 * - multipart/form-data 요청을 처리한다.
 * - 업로드된 파일을 서버 로컬 디스크에 저장한다.
 * - 파일명 충돌 방지를 위해 UUID를 사용한다.
 */
@Controller
public class FileSaveController {

    /**
     * 파일 업로드 요청 처리
     *
     * HTTP 요청 예시:
     * POST /upload/save
     * Content-Type: multipart/form-data
     * form-data:
     *   key = file, value = 업로드 파일
     */
    @PostMapping("/upload/save")
    @ResponseBody
    public String save(@RequestParam("file") MultipartFile file) throws IOException {

        // 1️⃣ 파일 유효성 검사
        // - 파일 파라미터가 없거나
        // - 파일이 비어 있는 경우 처리 중단
        if (file == null || file.isEmpty()) {
            return "파일이 비어 있습니다.";
        }

        // 2️⃣ 원본 파일명 추출
        // - 클라이언트가 업로드한 실제 파일 이름
        // - 브라우저 / OS 환경에 따라 null이 될 수 있으므로 방어 코드 필요
        String original = file.getOriginalFilename();
        if (original == null) {
            original = "unknown";
        }

        // 3️⃣ 보안 및 경로 문제 방지
        // - 일부 브라우저(특히 Windows)는 파일명에 경로를 포함해서 보낼 수 있음
        // - 경로 구분자(\, /)를 제거하지 않으면 의도치 않은 디렉터리 생성 위험
        original = original.replace("\\", "_").replace("/", "_");

        // 4️⃣ 저장 파일명 생성
        // - UUID를 앞에 붙여 파일명 충돌 방지
        // - 동일한 파일명이 업로드되어도 덮어쓰기 방지
        String savedName = UUID.randomUUID() + "_" + original;

        // 5️⃣ 업로드 디렉터리 경로 설정 (절대 경로)
        // - System.getProperty("user.dir") : 애플리케이션 실행 기준 디렉터리
        // - 상대 경로 사용 시 Tomcat work 디렉터리로 꼬이는 문제를 방지하기 위함
        Path uploadPath = Path
                .of(System.getProperty("user.dir"), "uploads")
                .toAbsolutePath();

        // 6️⃣ uploads 디렉터리가 없으면 생성
        // - 이미 존재하면 아무 작업도 하지 않음
        Files.createDirectories(uploadPath);

        // 7️⃣ 최종 저장 경로 생성
        // uploads/<UUID>_원본파일명
        Path dest = uploadPath.resolve(savedName);

        // 8️⃣ 실제 파일 저장
        // - MultipartFile → 로컬 파일로 변환
        // - 내부적으로 스트림 복사 수행
        file.transferTo(dest.toFile());

        // 9️⃣ 저장 결과 반환
        return "저장 완료: " + dest;
    }
}