package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example01;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component("firstBean")  // Bean 이름 명시적 지정
public class FirstBean {

    public FirstBean() {
        System.out.println("[Constructor] FirstBean 생성자 호출");
    }

    @PostConstruct
    public void init() {
        System.out.println("[PostConstruct] FirstBean 초기화 완료");
        // 초기화 로직: 설정 파일 로드, 리소스 준비 등
    }

    public String getData() {
        return "FirstBean의 데이터";
    }
}