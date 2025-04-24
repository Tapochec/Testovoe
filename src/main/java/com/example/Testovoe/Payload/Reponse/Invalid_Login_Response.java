package com.example.Testovoe.Payload.Reponse;

public class Invalid_Login_Response {
    private String email;
    private String password;

    public Invalid_Login_Response() {
        this.email = "Invalid Login";
        this.password = "Invalid Password";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
