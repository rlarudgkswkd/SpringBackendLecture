package com.codeit.springssedemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class SseConnectionManager {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000L;

    private final ConcurrentMap<String, Set<SseEmitter>> userConnections =
            new ConcurrentHashMap<>();

    public SseEmitter createConnection(String userId) {

        SseEmitter emitter =
                new SseEmitter(DEFAULT_TIMEOUT);

        userConnections.computeIfAbsent(
                userId,
                key -> ConcurrentHashMap.newKeySet()
        ).add(emitter);

        emitter.onCompletion(() -> {
            printLog("SSE 연결 완료");
            remove(userId, emitter);
        });

        emitter.onTimeout(() -> {
            printLog("SSE 연결 타임아웃");
            emitter.complete();
            remove(userId, emitter);
        });

        emitter.onError(exception -> {
            printLog("SSE 연결 오류: " + exception.getMessage());
            remove(userId, emitter);
        });

        sendConnectedEvent(userId, emitter);

        printLog("SSE 연결 등록: userId=" + userId);

        return emitter;
    }

    public void sendToUser(
            String userId,
            Object data
    ) {

        Set<SseEmitter> emitters =
                userConnections.get(userId);

        if (emitters == null || emitters.isEmpty()) {
            printLog("전송 대상 연결 없음: userId=" + userId);
            return;
        }

        Set<SseEmitter> failedEmitters =
                ConcurrentHashMap.newKeySet();

        for (SseEmitter emitter : emitters) {

            try {

                emitter.send(
                        SseEmitter.event()
                                .name("notification")
                                .id(UUID.randomUUID().toString())
                                .reconnectTime(3000L)
                                .data(data)
                );

            } catch (IOException e) {

                failedEmitters.add(emitter);
            }
        }

        if (!failedEmitters.isEmpty()) {

            emitters.removeAll(failedEmitters);
            printLog("실패한 SSE 연결 제거: count=" + failedEmitters.size());
        }
    }

    public int getConnectionCount(String userId) {

        Set<SseEmitter> emitters =
                userConnections.get(userId);

        if (emitters == null) {
            return 0;
        }

        return emitters.size();
    }

    private void sendConnectedEvent(
            String userId,
            SseEmitter emitter
    ) {

        try {

            emitter.send(
                    SseEmitter.event()
                            .name("connected")
                            .id("connect-" + System.currentTimeMillis())
                            .reconnectTime(3000L)
                            .data("알림 스트림에 연결되었습니다. userId=" + userId)
            );

        } catch (IOException e) {

            emitter.completeWithError(e);
            remove(userId, emitter);
        }
    }

    private void remove(
            String userId,
            SseEmitter emitter
    ) {

        Set<SseEmitter> emitters =
                userConnections.get(userId);

        if (emitters == null) {
            return;
        }

        emitters.remove(emitter);

        if (emitters.isEmpty()) {
            userConnections.remove(userId);
        }

        printLog(
                "SSE 연결 제거: userId="
                        + userId
                        + ", remaining="
                        + getConnectionCount(userId)
        );
    }

    private void printLog(String message) {

        System.out.println(
                "[" + LocalTime.now().withNano(0)
                        + "] [SseConnectionManager] "
                        + message
        );
    }
}