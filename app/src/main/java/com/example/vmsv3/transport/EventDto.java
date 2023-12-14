package com.example.vmsv3.transport;

import com.google.gson.annotations.SerializedName;

public class EventDto {
    @SerializedName("nazwa")
    private String name;
    @SerializedName("opis")
    private String description;
    @SerializedName("data")
    private String date;
    @SerializedName("koszt")
    private int koszt;
    @SerializedName("czy_przypomniec")
    private int remind;
    @SerializedName("czy_okresowe")
    private int repeatable;
    @SerializedName("okres")
    private int interval;
    @SerializedName("id_pojazdu")
    private long vehicleId;

    public EventDto() {
    }

    public EventDto(String name, String description, String date, int koszt, int remind, int repeatable, int interval, long vehicleId) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.koszt = koszt;
        this.remind = remind;
        this.repeatable = repeatable;
        this.interval = interval;
        this.vehicleId = vehicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getKoszt() {
        return koszt;
    }

    public void setKoszt(int koszt) {
        this.koszt = koszt;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(int repeatable) {
        this.repeatable = repeatable;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
