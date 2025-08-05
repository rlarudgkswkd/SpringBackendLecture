package org.example.Head06_SpringOverview.topic03_springCoreConept.example02;

import java.util.List;

public class MenuServiceStub implements MenuService {
    @Override
    public List<String> getMenuList() {
        return List.of("샘플커피1", "샘플커피2");
    }
}