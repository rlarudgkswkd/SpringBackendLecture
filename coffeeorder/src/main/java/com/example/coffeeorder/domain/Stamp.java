package com.example.coffeeorder.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "stamps")
public class Stamp {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    @Column(name = "stamp_count", nullable = false)
    private int stampCount;

    protected Stamp() {}

    public int getStampCount() { return stampCount; }

    public void increase(int amount) {
        this.stampCount += amount;
    }
}