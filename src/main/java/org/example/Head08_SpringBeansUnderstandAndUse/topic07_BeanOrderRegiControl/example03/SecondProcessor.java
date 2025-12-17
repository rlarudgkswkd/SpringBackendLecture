package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example03;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// 두 번째 BeanPostProcessor
@Component
public class SecondProcessor implements BeanPostProcessor, Ordered {

    @Override
    public int getOrder() {
        // 2 > 1 이므로 FirstProcessor 다음에 실행
        return 2;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        // @Service 붙은 Bean 전처리
        if (bean.getClass().isAnnotationPresent(Service.class)) {
            System.out.println("[Order 2] Service Bean 전처리(before): " + beanName);
            // 여기서 프록시 래핑, 공통 설정 적용 등을 할 수 있음
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean.getClass().isAnnotationPresent(Service.class)) {
            System.out.println("[Order 2] Service Bean 후처리(after): " + beanName);
            // 예: 메트릭 수집, 로깅 프록시 적용 등
        }
        return bean;
    }
}