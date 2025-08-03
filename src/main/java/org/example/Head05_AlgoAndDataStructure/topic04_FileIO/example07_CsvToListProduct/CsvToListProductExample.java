package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example07_CsvToListProduct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvToListProductExample {

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
        // constructor, getters, setters...
    }

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("products.csv"))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) { // 첫 줄 헤더 건너뛰기
                    header = false;
                    continue;
                }
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                int price = Integer.parseInt(tokens[2]);

                products.add(new Product(id, name, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("CSV로부터 로드된 Product List: " + products);
    }
}
