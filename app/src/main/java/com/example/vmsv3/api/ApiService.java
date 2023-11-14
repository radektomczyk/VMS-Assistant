package com.example.vmsv3.api;

import com.example.vmsv3.transport.CostDto;
import com.example.vmsv3.transport.LoginDto;
import com.example.vmsv3.transport.RefuelDto;
import com.example.vmsv3.transport.TicketDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginDto loginDto);

    @POST("/api/tickets")
    Call<Void> createTicket(@Header("Authorization") String authorizationHeader, @Body TicketDto ticketDto);

    @POST("/api/refuel/{vehicleid}")
    Call<Void> createRefuel(@Header("Authorization") String authorozationHeader, @Body RefuelDto refuelDto);

    @POST("/api/costs/")
    Call<Void> createCost(@Header("Authorization") String authorizationHeader, @Body CostDto costDto);
}