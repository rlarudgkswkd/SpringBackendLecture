package com.example.coffeeorder.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    private Long id; // import.sql에서 직접 넣기 위해 IDENTITY 사용 안 함

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    protected Member() {} // JPA용 (어노테이션 NoArgsConstructor 미사용)

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
}
