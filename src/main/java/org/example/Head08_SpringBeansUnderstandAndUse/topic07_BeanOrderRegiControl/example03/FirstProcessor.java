package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example03;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

// 첫 번째 BeanPostProcessor
@Component
public class FirstProcessor implements BeanPostProcessor, Ordered {

    @Override
    public int getOrder() {
        // 숫자가 낮을수록 먼저 실행
        return 1;  // [Order 1]
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        // DataSource Bean 전처리
        if (bean instanceof DataSource) {
            System.out.println("[Order 1] DataSource Bean 전처리(before): " + beanName);
            // 여기서 DataSource 설정 조정 가능
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof DataSource) {
            System.out.println("[Order 1] DataSource Bean 후처리(after): " + beanName);
            // 연결 풀 모니터링 설정 등
        }
        return bean;
    }
}