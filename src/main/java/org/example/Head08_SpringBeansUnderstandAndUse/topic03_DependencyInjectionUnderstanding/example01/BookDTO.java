package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example01;

public class BookDTO {

    private int sequence;
    private String title;
    private String author;
    private int price;

    public BookDTO(int sequence, String title, String author, int price) {
        this.sequence = sequence;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getSequence() { return sequence; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return "[" + sequence + "] " + title + " / " + author + " / " + price + "원";
    }
}
