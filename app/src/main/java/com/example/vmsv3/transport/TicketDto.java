package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

public class TicketDto {
    @SerializedName("nazwa")
    private  String reason;
    @SerializedName("liczba_punktow")
    private  int penaltyPoints;
    @SerializedName("waznosc")
    private  int validityMonths;
    @SerializedName("data_wystawienia")
    private String receiveDate;
    @SerializedName("cena")
    private  int amount;
    @SerializedName("id_user")
    private  int userId;
    public TicketDto(){}

    public TicketDto(String reason, int penaltyPoints, int validityMonths, String receiveDate, int amount, int userId) {
        this.reason = reason;
        this.penaltyPoints = penaltyPoints;
        this.validityMonths = validityMonths;
        this.receiveDate = receiveDate;
        this.amount = amount;
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
    }

    public int getValidityMonths() {
        return validityMonths;
    }

    public void setValidityMonths(int validityMonths) {
        this.validityMonths = validityMonths;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return userId;
    }

    public void setId(int userId) {
        this.userId = userId;
    }
}
