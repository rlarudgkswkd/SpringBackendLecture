package org.example.Head06_SpringOverview.topic03_springCoreConept.example01_DIExampleUsingExtends;

import java.util.List;

public class MenuService {
    public List<String> getMenuList() {
        return List.of("아메리카노", "카페라떼");
    }
}