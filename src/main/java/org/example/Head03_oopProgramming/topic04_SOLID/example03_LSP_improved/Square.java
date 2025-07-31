package org.example.Head03_oopProgramming.topic04_SOLID.example03_LSP_improved;

public class Square extends Shape {
    private int side;

    public Square(int side) {
        setSide(side);
    }

    public void setSide(int side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side must be > 0");
        }
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}

