package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

public class CostDto {
    @SerializedName("id_kosztu")
    private int id;
    @SerializedName("nazwa")
    private String costName;
    @SerializedName("opis")
    private String costDescription;
    @SerializedName("koszt")
    private int costAmount;
    @SerializedName("data")
    private String costDate;
    @SerializedName("id_user")
    private int userId;

    public CostDto() {}

    public CostDto(String costName, String costDescription, int costAmount, String costDate) {
        this.costName = costName;
        this.costDescription = costDescription;
        this.costAmount = costAmount;
        this.costDate = costDate;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getCostDescription() {
        return costDescription;
    }

    public void setCostDescription(String costDescription) { this.costDescription = costDescription; }

    public int getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(int costAmount) {
        this.costAmount = costAmount;
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }
}
