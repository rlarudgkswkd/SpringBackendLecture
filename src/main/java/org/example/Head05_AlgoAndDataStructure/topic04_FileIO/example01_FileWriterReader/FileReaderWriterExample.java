package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example01_FileWriterReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderWriterExample {
    public static void main(String[] args) {
        // FileReader로 텍스트 파일을 한 글자씩 읽는 예시
        try (FileReader fr = new FileReader("example.txt")) {
            int data;
            while ((data = fr.read()) != -1) {
                System.out.print((char) data); // 읽은 문자를 출력
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // FileWriter로 텍스트 파일에 한 글자씩 쓰는 예시
        try (FileWriter fw = new FileWriter("output.txt")) {
            String content = "Hello File I/O";
            for (char c : content.toCharArray()) {
                fw.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
