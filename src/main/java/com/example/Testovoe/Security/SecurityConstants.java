package com.example.Testovoe.Security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "api/auth/**";

    public static final String SECRET = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTc0NTU5MjY3N30fWcNCnxgrMXmnZFe4bYJIbn1e5yODdn6y1efR9LQCGVbqrKWNVeqFAibNBXCbq2GHmhfBzvlF0GzAfL3puOw";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 600_00000;


}
