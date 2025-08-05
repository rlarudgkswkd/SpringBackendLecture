package org.example.Head06_SpringOverview.topic03_springCoreConept.example01_DIExampleUsingExtends;

import java.util.List;

public class MenuServiceStub extends MenuService {
    @Override
    public List<String> getMenuList() {
        return List.of("샘플커피1", "샘플커피2");
    }
}