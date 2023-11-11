package com.example.vmsv3.api;

import com.example.vmsv3.transport.LoginDto;
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
}