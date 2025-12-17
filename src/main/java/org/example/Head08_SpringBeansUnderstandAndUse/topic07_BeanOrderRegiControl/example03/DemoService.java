package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example03;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public void doWork() {
        System.out.println("DemoService.doWork() 실행");
    }
}
