package com.example.springtdd.Head05_SpringServiceLayerTest.repository;

import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Member;

public interface DiscountPolicy {
    int calculateDiscount(Member member, int price);
}
