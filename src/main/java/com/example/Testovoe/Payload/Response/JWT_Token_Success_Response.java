package com.example.Testovoe.Payload.Response;

public class JWT_Token_Success_Response {
    private boolean success;
    private String token;

    public JWT_Token_Success_Response(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
