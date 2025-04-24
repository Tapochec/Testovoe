package com.example.Testovoe.Repository;

import com.example.Testovoe.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface User_repository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE EXISTS (SELECT 1 FROM u.emails e WHERE e.email = :email)")
    Optional<User> findUserByEmails(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE EXISTS (SELECT 1 FROM u.phones e WHERE e.phone = :phone)")
    Optional<User> findUserByPhones(@Param("phone") String phone);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByName(String name);

    @Query("SELECT u FROM User u WHERE u.date_of_birth = :dateOfBirth")
    Optional<User> findUserByDate_of_birth(@Param("dateOfBirth") LocalDateTime dateOfBirth);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
