package org.example.springdatajpaexample.repository;

import org.example.springdatajpaexample.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByNameContaining(String keyword);

    List<Menu> findByPriceBetween(int min, int max);

    boolean existsByPriceGreaterThan(int price);

    List<Menu> findByCategoryId(Long categoryId);

    @Query("""
        SELECT m
        FROM Menu m
        WHERE m.category.name = :categoryName
          AND m.price >= :minPrice
        ORDER BY m.price DESC
    """)
    List<Menu> findByCategoryNameAndMinPrice(
            @Param("categoryName") String categoryName,
            @Param("minPrice") int minPrice
    );

    List<Menu> findByCategoryNameAndPriceGreaterThanEqualOrderByPriceDesc(
            String categoryName,
            int minPrice
    );

}