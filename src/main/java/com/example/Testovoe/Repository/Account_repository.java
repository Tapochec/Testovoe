package com.example.Testovoe.Repository;

import com.example.Testovoe.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Account_repository extends JpaRepository<Account, Long> {
}
