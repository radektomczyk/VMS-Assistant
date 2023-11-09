package com.example.vmsv3.transport;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class MandatDto {
    @JsonProperty("nazwa")
    private final String reason;
    @JsonProperty("liczba_punktow")
    private final int penaltyPoints;
    @JsonProperty("waznosc")
    private final int validityMonths;
    @JsonProperty("data_wystawienia")
    private final LocalDate receiveDate;
    @JsonProperty("cena")
    private final int amount;

    public MandatDto(String reason, int penaltyPoints, int validityMonths, LocalDate receiveDate, int amount) {
        this.reason = reason;
        this.penaltyPoints = penaltyPoints;
        this.validityMonths = validityMonths;
        this.receiveDate = receiveDate;
        this.amount = amount;
    }
}
