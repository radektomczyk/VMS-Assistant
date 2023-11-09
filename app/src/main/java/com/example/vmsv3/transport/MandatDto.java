package com.example.vmsv3.transport;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Date;

public class MandatDto {
    @JsonProperty("nazwa")
    private final String reason;
    @JsonProperty("liczba_punktow")
    private final int penaltyPoints;
    @JsonProperty("waznosc")
    private final int validityMonths;
    @JsonProperty("data_wystawienia")
    private final Date receiveDate;
    @JsonProperty("cena")
    private final int amount;
    @JsonProperty("id_user")
    private final int id;

    public MandatDto(String reason, int penaltyPoints, int validityMonths, Date receiveDate, int amount, int id) {
        this.reason = reason;
        this.penaltyPoints = penaltyPoints;
        this.validityMonths = validityMonths;
        this.receiveDate = receiveDate;
        this.amount = amount;
        this.id = id;
    }
}
