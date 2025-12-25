package topic05_RestFulDocument.example01.dto;

public class RestDocsMemberResponse {
    private Long id;
    private String name;
    private String email;

    public RestDocsMemberResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}