package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.util;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.HexFormat;
import java.util.UUID;

public class UploadPathPolicy {

    // 목적별 "논리 경로" (2개)
    public static final String PROFILE = "profile";
    public static final String ATTACHMENTS = "attachments";

    // 실무 스타일: 알아보기 어려운 prefix 생성 (해시 + 날짜 + 목적)
    public static String generateObscuredPrefix(String purpose) {
        String seed = purpose + ":" + LocalDate.now() + ":" + UUID.randomUUID();
        return purpose + "/" + hash(seed).substring(0, 12) + "/" + hash(seed).substring(12, 24);
    }

    private static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            // 해시 실패 시 최소 안전 장치
            return UUID.randomUUID().toString().replace("-", "");
        }
    }
}