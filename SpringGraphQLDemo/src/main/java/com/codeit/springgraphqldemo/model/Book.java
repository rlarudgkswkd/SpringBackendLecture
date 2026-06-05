package com.codeit.springgraphqldemo.model;

public class Book {

    private Long id;
    private String title;
    private String author;
    private Integer price;

    public Book(
            Long id,
            String title,
            String author,
            Integer price
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPrice() {
        return price;
    }
}