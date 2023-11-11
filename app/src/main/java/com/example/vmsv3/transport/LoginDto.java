package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

public class LoginDto {
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;

    public LoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
