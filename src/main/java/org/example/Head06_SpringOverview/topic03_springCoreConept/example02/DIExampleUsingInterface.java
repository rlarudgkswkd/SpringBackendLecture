package org.example.Head06_SpringOverview.topic03_springCoreConept.example02;

public class DIExampleUsingInterface {
    public static void main(String[] args) {
        // 실제 운영 환경
        MenuService realService = new MenuServiceImpl();
        MenuController controller = new MenuController(realService);
        System.out.println("==실제 운영 예시==");
        controller.printMenu();

        // 테스트 또는 Stub 사용 환경
        MenuService stubService = new MenuServiceStub();
        MenuController testController = new MenuController(stubService);
        System.out.println("\n==테스트용 혹은 Stub 예시==");
        testController.printMenu();
    }
}