package com.example.Testovoe.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails{
    public User() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String name;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime date_of_birth;

    @Column(length = 500)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Account account;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone_data> phones = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Email_data> emails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDateTime date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Phone_data> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone_data> phones) {
        this.phones = phones;
    }



    public User(Long id, String name, LocalDateTime date_of_birth, String password, Account account, List<Phone_data> phones, List<Email_data> emails) {
        this.id = id;
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.account = account;
        this.phones = phones;
        this.emails = emails;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }

    public List<Email_data> getEmails() {
        return emails;
    }

    public void setEmails(List<Email_data> emails) {
        this.emails = emails;
    }
}
