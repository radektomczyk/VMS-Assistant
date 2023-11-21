package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

public class PermissionDto {
    @SerializedName("id")
    private int id;

    @SerializedName("kategoria")
    private String category;

    @SerializedName("id_user")
    private int userId;

    public PermissionDto() {
    }

    public PermissionDto(int id, String category, int userId) {
        this.id = id;
        this.category = category;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
