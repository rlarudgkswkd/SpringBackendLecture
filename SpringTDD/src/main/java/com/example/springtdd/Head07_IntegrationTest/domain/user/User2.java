package com.example.springtdd.Head07_IntegrationTest.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User2 {

    @Id @GeneratedValue
    private Long id;

    private String email;
    private String name;
    private int points;

    @Enumerated(EnumType.STRING)
    private UserGrade grade;

    public User2(String email, String name, UserGrade grade) {
        this.email = email;
        this.name = name;
        this.grade = grade;
        this.points = 0;
    }

    public void addPoints(int amount) {
        this.points += amount;
    }
}
