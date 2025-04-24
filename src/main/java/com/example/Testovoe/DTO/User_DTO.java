package com.example.Testovoe.DTO;

import com.example.Testovoe.Entity.Email_data;
import com.example.Testovoe.Entity.Phone_data;

import java.util.List;

public class User_DTO {
    private Long id;
    private String name;
    private List<Email_data> emails;
    private List<Phone_data> phones;

    public List<Email_data> getEmails() {
        return emails;
    }

    public void setEmails(List<Email_data> emails) {
        this.emails = emails;
    }

    public List<Phone_data> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone_data> phones) {
        this.phones = phones;
    }

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


}
