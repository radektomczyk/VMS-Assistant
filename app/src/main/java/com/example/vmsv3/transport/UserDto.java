package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDto {
    @SerializedName("id_user")
    private int userId;
    @SerializedName("imie")
    private String name;
    @SerializedName("nazwisko")
    private String surname;
    @SerializedName("email")
    private String email;
    @SerializedName("login")
    private String login;
    @SerializedName("rola")
    private String rola;
    @SerializedName("status")
    private String status;
    @SerializedName("permissions")
    private List<PermissionDto> permissions;

    public UserDto() {
    }

    public UserDto(int userId, String name, String surname, String email, String login, String rola, String status, List<PermissionDto> permissions) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.rola = rola;
        this.status = status;
        this.permissions = permissions;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }
}
