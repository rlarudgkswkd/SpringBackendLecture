package org.example.Head09_SpringMVC.topic03_SpringMVCArchi.example01;

public class UserDto {
    private Long id;
    private String name;
    private int age;

    public UserDto() { } // Jackson용 기본 생성자 (권장)

    public UserDto(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
}