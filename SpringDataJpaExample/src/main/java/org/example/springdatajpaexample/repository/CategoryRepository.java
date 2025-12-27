package org.example.springdatajpaexample.repository;

import org.example.springdatajpaexample.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}