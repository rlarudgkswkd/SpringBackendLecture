package com.codeit.springsecurityadvancedsession.domain;

public class Post {

    private Long id;

    private String title;

    private String content;

    private String author;

    public Post(
            Long id,
            String title,
            String content,
            String author
    ) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
