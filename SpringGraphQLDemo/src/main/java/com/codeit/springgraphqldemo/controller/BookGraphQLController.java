package com.codeit.springgraphqldemo.controller;

import com.codeit.springgraphqldemo.dto.CreateBookInput;
import com.codeit.springgraphqldemo.model.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookGraphQLController {

    private final List<Book> books =
            new ArrayList<>();

    private Long sequence = 1L;

    public BookGraphQLController() {

        books.add(
                new Book(
                        sequence++,
                        "자바 입문",
                        "Codeit",
                        25000
                )
        );

        books.add(
                new Book(
                        sequence++,
                        "Spring Boot 기초",
                        "Codeit",
                        32000
                )
        );
    }

    @QueryMapping
    public List<Book> books() {

        printLog("books Query 실행");

        return books;
    }

    @QueryMapping
    public Book book(
            @Argument Long id
    ) {

        printLog("book Query 실행: id=" + id);

        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @MutationMapping
    public Book createBook(
            @Argument CreateBookInput input
    ) {

        printLog(
                "createBook Mutation 실행: title="
                        + input.title()
        );

        Book book =
                new Book(
                        sequence++,
                        input.title(),
                        input.author(),
                        input.price()
                );

        books.add(book);

        return book;
    }

    private void printLog(String message) {

        System.out.println(
                "[" + LocalTime.now().withNano(0)
                        + "] [BookGraphQLController] "
                        + message
        );
    }
}