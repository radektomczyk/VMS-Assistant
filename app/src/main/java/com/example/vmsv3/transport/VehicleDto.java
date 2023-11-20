package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

public class VehicleDto {
    @SerializedName("marka")
    private String carBrand;
    @SerializedName("model")
    private String carModel;
    @SerializedName("rocznik")
    private int carYear;
    @SerializedName("VIN")
    private String carVIN;
    @SerializedName("nr_rejestracyjny")
    private String carPlate;
    @SerializedName("data_pierw_rej")
    private String carRegistrationDate;
    @SerializedName("typ_paliwa")
    private String carFuelType;
    @SerializedName("kategoria")
    private String carCategory;

    public VehicleDto() {
    }

    public VehicleDto(String carBrand, String carModel, int carYear, String carVIN, String carPlate, String carRegistrationDate, String carFuelType, String carCategory) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carYear = carYear;
        this.carVIN = carVIN;
        this.carPlate = carPlate;
        this.carRegistrationDate = carRegistrationDate;
        this.carFuelType = carFuelType;
        this.carCategory = carCategory;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public String getCarVIN() {
        return carVIN;
    }

    public void setCarVIN(String carVIN) {
        this.carVIN = carVIN;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarRegistrationDate() {
        return carRegistrationDate;
    }

    public void setCarRegistrationDate(String carRegistrationDate) {
        this.carRegistrationDate = carRegistrationDate;
    }

    public String getCarFuelType() {
        return carFuelType;
    }

    public void setCarFuelType(String carFuelType) {
        this.carFuelType = carFuelType;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }
}
