package org.example.Head11_RestFulAPiDesignAndImplement.topic05_RestFulDocument.example02.dto;

public class SwaggerMemberResponse {
    private Long id;
    private String name;
    private String email;

    public SwaggerMemberResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}