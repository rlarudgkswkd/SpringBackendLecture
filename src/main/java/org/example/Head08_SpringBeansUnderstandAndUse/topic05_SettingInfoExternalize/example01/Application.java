package org.example.Head08_SpringBeansUnderstandAndUse.topic05_SettingInfoExternalize.example01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Application {
    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);

        AppPropertiesExample props =
                context.getBean(AppPropertiesExample.class);

        props.printProperties();
    }
}