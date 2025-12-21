package org.example.Head09_SpringMVC.topic09_ResponseDataProcess.example07;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DownloadPageController {

    @GetMapping(value = "/download", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String page() {
        return """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"><title>Download</title></head>
            <body>
                <h2>파일 다운로드 실습</h2>
                <ul>
                    <li><a href="/v1/files/download-txt">sample.txt 다운로드</a></li>
                    <li><a href="/v1/files/download-korean">보고서_샘플.txt 다운로드</a></li>
                </ul>
            </body>
            </html>
        """;
    }
}
