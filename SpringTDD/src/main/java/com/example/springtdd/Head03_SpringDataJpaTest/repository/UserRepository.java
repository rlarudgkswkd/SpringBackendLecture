package com.example.springtdd.Head03_SpringDataJpaTest.repository;

import com.example.springtdd.Head03_SpringDataJpaTest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByStatus(String status);

    List<User> findByStatusAndName(String status, String name);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailWithQuery(@Param("email") String email);

    Object existsByEmail(String mail);
}
