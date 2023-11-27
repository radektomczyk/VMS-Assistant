package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

public class RefuelDto {
    @SerializedName("ilosc_paliwa")
    private double  fuelAmount;
    @SerializedName("typ_paliwa")
    private String fuelType;
    @SerializedName("cena_za_litr")
    private int pricePerLiter;
    @SerializedName("cena")
    private int totalPrice;
    @SerializedName("data")
    private String refuelDate;
    @SerializedName("blokada")
    private int blockade;
    @SerializedName("id_pojazdu")
    private long vehicleId;

    public RefuelDto() {}

    public RefuelDto(double fuelAmount, String fuelType, int pricePerLiter, int totalPrice, String refuelDate, int blockade, long vehicleId) {
        this.fuelAmount = fuelAmount;
        this.fuelType = fuelType;
        this.pricePerLiter = pricePerLiter;
        this.totalPrice = totalPrice;
        this.refuelDate = refuelDate;
        this.blockade = blockade;
        this.vehicleId = vehicleId;
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

    public int getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(int pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRefuelDate() {
        return refuelDate;
    }

    public void setRefuelDate(String refuelDate) {
        this.refuelDate = refuelDate;
    }

    public int isBlockade() {
        return blockade;
    }

    public void setBlockade(int blockade) {
        this.blockade = blockade;
    }

    public int getBlockade() {
        return blockade;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
