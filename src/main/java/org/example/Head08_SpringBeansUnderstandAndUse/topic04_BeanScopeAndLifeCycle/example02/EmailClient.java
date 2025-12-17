package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example02;

public class EmailClient {

    public void connect() {
        System.out.println("[EmailClient] 서버 연결 시도...");
    }

    public void disconnect() {
        System.out.println("[EmailClient] 서버 연결 해제...");
    }
}