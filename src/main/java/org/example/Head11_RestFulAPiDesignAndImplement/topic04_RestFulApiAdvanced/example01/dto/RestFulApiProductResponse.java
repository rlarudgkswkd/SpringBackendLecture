package org.example.Head11_RestFulAPiDesignAndImplement.topic04_RestFulApiAdvanced.example01.dto;

public class RestFulApiProductResponse {
    private Long id;
    private String name;

    public RestFulApiProductResponse() { } // (JSON 직렬화/역직렬화 호환)

    public RestFulApiProductResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}