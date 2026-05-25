package com.codeit.javathreadbasicdemo.practice01;

public class SyncAsyncComparisonDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 1.4 [실습] 동기 처리와 비동기 처리 성능 비교 ===");

        runSynchronousProcessing();

        System.out.println();

        runAsynchronousProcessing();
    }

    private static void runSynchronousProcessing() {

        System.out.println("[동기 처리 시작]");

        long startTime =
                System.currentTimeMillis();

        downloadFile(
                "document.pdf",
                2000
        );

        downloadFile(
                "image.jpg",
                1500
        );

        downloadFile(
                "video.mp4",
                4000
        );

        long endTime =
                System.currentTimeMillis();

        System.out.println(
                "[동기 처리 종료] 총 소요 시간: "
                        + (endTime - startTime)
                        + "ms"
        );
    }

    private static void runAsynchronousProcessing() {

        System.out.println("[비동기 처리 시작]");

        long startTime =
                System.currentTimeMillis();

        Thread documentThread =
                new Thread(() ->
                        downloadFile(
                                "document.pdf",
                                2000
                        )
                );

        Thread imageThread =
                new Thread(() ->
                        downloadFile(
                                "image.jpg",
                                1500
                        )
                );

        Thread videoThread =
                new Thread(() ->
                        downloadFile(
                                "video.mp4",
                                4000
                        )
                );

        documentThread.setName("DocumentDownloadThread");
        imageThread.setName("ImageDownloadThread");
        videoThread.setName("VideoDownloadThread");

        documentThread.start();
        imageThread.start();
        videoThread.start();

        try {
            documentThread.join();
            imageThread.join();
            videoThread.join();

        } catch (InterruptedException e) {

            System.out.println("메인 스레드가 인터럽트되었습니다.");

            Thread.currentThread()
                    .interrupt();
        }

        long endTime =
                System.currentTimeMillis();

        System.out.println(
                "[비동기 처리 종료] 총 소요 시간: "
                        + (endTime - startTime)
                        + "ms"
        );
    }

    private static void downloadFile(
            String fileName,
            int delayMillis
    ) {

        String threadName =
                Thread.currentThread()
                        .getName();

        try {
            System.out.println(
                    "[" + threadName + "] 다운로드 시작: "
                            + fileName
            );

            Thread.sleep(delayMillis);

            System.out.println(
                    "[" + threadName + "] 다운로드 완료: "
                            + fileName
            );

        } catch (InterruptedException e) {

            System.out.println(
                    "[" + threadName + "] 다운로드 중 인터럽트 발생: "
                            + fileName
            );

            Thread.currentThread()
                    .interrupt();
        }
    }
}
