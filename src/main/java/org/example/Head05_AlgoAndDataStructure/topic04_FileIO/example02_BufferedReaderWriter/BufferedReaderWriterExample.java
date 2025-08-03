package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example02_BufferedReaderWriter;

import java.io.*;

public class BufferedReaderWriterExample {
    public static void main(String[] args) {
        // BufferedReader로 텍스트 파일을 한 줄씩 읽는 예시
        try (BufferedReader br = new BufferedReader(new FileReader("example.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line); // 한 줄씩 출력
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // BufferedWriter로 텍스트 파일에 한 줄씩 쓰는 예시
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {
            bw.write("Hello File I/O");
            bw.newLine(); // 줄바꿈
            bw.write("Using BufferedWriter for better performance");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
