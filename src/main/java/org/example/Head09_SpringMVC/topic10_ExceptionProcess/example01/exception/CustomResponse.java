package org.example.Head09_SpringMVC.topic10_ExceptionProcess.example01.exception;

public class CustomResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;

    private CustomResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 정상 응답용
    public static <T> CustomResponse<T> success(T data) {
        return new CustomResponse<>(true, "SUCCESS", null, data);
    }

    // 에러 응답용
    public static <T> CustomResponse<T> error(String code, String message) {
        return new CustomResponse<>(false, code, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
