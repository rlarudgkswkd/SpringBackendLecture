package org.example.oopProgramming.topic04_SOLID.example03_LSP;

public class Square extends Rectangle {
    // width, height가 항상 동일해야 하는 제약
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // 강제로 같게 맞춤
    }

    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}

