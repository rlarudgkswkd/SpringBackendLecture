package org.example.Head09_SpringMVC.topic08_FileUploadProcess.example04.dto;

public class UploadResult {
    private final String originalName;
    private final String savedName;
    private final String savedPath;   // 로컬 저장 경로
    private final String s3Key;       // "S3에 저장된 것처럼 보이는 키"
    private final long size;

    public UploadResult(String originalName, String savedName, String savedPath, String s3Key, long size) {
        this.originalName = originalName;
        this.savedName = savedName;
        this.savedPath = savedPath;
        this.s3Key = s3Key;
        this.size = size;
    }

    public String getOriginalName() { return originalName; }
    public String getSavedName() { return savedName; }
    public String getSavedPath() { return savedPath; }
    public String getS3Key() { return s3Key; }
    public long getSize() { return size; }

    @Override
    public String toString() {
        return "UploadResult{" +
                "originalName='" + originalName + '\'' +
                ", savedName='" + savedName + '\'' +
                ", savedPath='" + savedPath + '\'' +
                ", s3Key='" + s3Key + '\'' +
                ", size=" + size +
                '}';
    }
}