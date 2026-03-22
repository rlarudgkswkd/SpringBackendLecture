package com.example.springtdd.Head04_MockitoBasic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_new")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true) // 🔥 중요
public class UserNew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    @Column
    private String password; // 🔥 추가
}