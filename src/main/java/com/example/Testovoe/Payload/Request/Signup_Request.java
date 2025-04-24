package com.example.Testovoe.Payload.Request;

import com.example.Testovoe.Annotation.Valid_email;
import com.example.Testovoe.Annotation.Valid_phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class Signup_Request {
    @Email(message = "Это не похоже на почту")
    @NotBlank(message = "Пользователь с таким емейлом уже существует")
    @Valid_email.ValidEmail
    private String email;
    @NotEmpty(message = "Имя введи, дурень")
    private String name;
    @Valid_phone.ValidPhone
    @NotBlank(message = "Пользователь с таким телефоном уже существует")
    private String phone;
    @NotEmpty(message = "Введите пароль")
    private String password;
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
