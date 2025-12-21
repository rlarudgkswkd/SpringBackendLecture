package org.example.Head09_SpringMVC.topic09_ResponseDataProcess.example07;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileSetupRunner implements CommandLineRunner {

    public static final Path BASE_DIR =
            Path.of(System.getProperty("user.dir"), "files").toAbsolutePath();

    public static final Path SAMPLE_TXT = BASE_DIR.resolve("sample.txt");
    public static final Path SAMPLE_KO = BASE_DIR.resolve("보고서_샘플.txt");

    @Override
    public void run(String... args) throws Exception {
        Files.createDirectories(BASE_DIR);

        if (Files.notExists(SAMPLE_TXT)) {
            Files.writeString(SAMPLE_TXT,
                    "This is sample.txt\nFile download demo.\n",
                    StandardCharsets.UTF_8);
        }

        if (Files.notExists(SAMPLE_KO)) {
            Files.writeString(SAMPLE_KO,
                    "보고서 샘플 파일입니다.\n한글 파일명 테스트.\n",
                    StandardCharsets.UTF_8);
        }
    }
}