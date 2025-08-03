package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example08_ListProductToCsv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ListProductToCsvExample {

    private static class Product {
        private int id;
        private String name;
        private int price;
        public Product(int id, String name, int price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        // constructor, getters, setters...
    }

    public static void main(String[] args) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("products.csv"))) {
            // 헤더 작성
            bw.write("id,name,price");
            bw.newLine();
            Product[] products = new Product[] {
                    new Product(4,"Carrot",500),
                    new Product(5,"Potato",200),
                    new Product(6,"Pear",10000),
            };

            for (Product p : products) {
                bw.write(p.getId() + "," + p.getName() + "," + p.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
