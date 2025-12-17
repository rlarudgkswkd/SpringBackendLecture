package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example01;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("firstBean")  // firstBean이 먼저 초기화되도록 보장
public class SecondBean {

    /*
     * 주의:
     * - @DependsOn은 "초기화 순서"만 보장하고,
     *   의존성 주입(@Autowired)을 대신해주지는 않는다.
     * - 실제로 FirstBean을 사용하려면 아래처럼 주입이 필요하다.
     */
    @Autowired
    private FirstBean firstBean;

    public SecondBean() {
        System.out.println("[Constructor] SecondBean 생성자 호출");
        // 이 시점에는 firstBean이 생성은 되었지만,
        // 아직 @Autowired 주입은 끝나지 않은 상태일 수 있음
    }

    @PostConstruct
    public void init() {
        System.out.println("[PostConstruct] SecondBean 초기화 시작");
        // 이제는 firstBean 주입이 완료된 상태이므로 안전하게 사용 가능
        String data = firstBean.getData();
        System.out.println("FirstBean으로부터 받은 데이터: " + data);
        System.out.println("[PostConstruct] SecondBean 초기화 완료");
    }
}