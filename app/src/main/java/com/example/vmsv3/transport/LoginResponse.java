package com.example.vmsv3.transport;

public class LoginResponse {
    private final String access_token;

    public LoginResponse(String accessToken) {
        access_token = accessToken;
    }

    public String getAccessToken() {
        return access_token;
    }
}
