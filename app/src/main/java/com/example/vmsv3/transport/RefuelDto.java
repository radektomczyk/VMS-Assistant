package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

public class RefuelDto {
    @SerializedName("ilosc_paliwa")
    private double  fuelAmount;
    @SerializedName("typ_paliwa")
    private String fuelType;
    @SerializedName("cena_za_litr")
    private double pricePerLiter;
    @SerializedName("cena")
    private double totalPrice;
    @SerializedName("data")
    private String refuelDate;
    @SerializedName("blokada")
    private boolean blockade;

    public RefuelDto() {}

    public RefuelDto(double fuelAmount, String fuelType, double pricePerLiter, double totalPrice, String refuelDate, boolean blockade) {
        this.fuelAmount = fuelAmount;
        this.fuelType = fuelType;
        this.pricePerLiter = pricePerLiter;
        this.totalPrice = totalPrice;
        this.refuelDate = refuelDate;
        this.blockade = blockade;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(double pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRefuelDate() {
        return refuelDate;
    }

    public void setRefuelDate(String refuelDate) {
        this.refuelDate = refuelDate;
    }

    public boolean isBlockade() {
        return blockade;
    }

    public void setBlockade(boolean blockadel) {
        this.blockade = blockadel;
    }
}
