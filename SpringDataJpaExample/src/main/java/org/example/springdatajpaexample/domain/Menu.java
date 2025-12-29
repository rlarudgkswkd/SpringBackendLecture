package org.example.springdatajpaexample.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name = "menus")
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_name", nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY) // 실무 권장
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    protected Menu() { }

    public Menu(String name, int price, Category category) {
        this.name = name;
        this.price = price;
        this.createdAt = Instant.now();
        this.category = category;
    }

    // 연관관계 편의 메서드(선택)
    public void changeCategory(Category category) {
        this.category = category;
    }
    // org.example.springdatajpaexample.domain.Menu

    public void increasePrice(int delta) {
        this.price += delta;
    }
}