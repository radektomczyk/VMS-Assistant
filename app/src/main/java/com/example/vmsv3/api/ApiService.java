package com.example.vmsv3.api;

import com.example.vmsv3.transport.CostDto;
import com.example.vmsv3.transport.LoginDto;
import com.example.vmsv3.transport.RefuelDto;
import com.example.vmsv3.transport.TicketDto;
import com.example.vmsv3.transport.UserDto;
import com.example.vmsv3.transport.VehicleDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginDto loginDto);

    @POST("/api/tickets")
    Call<Void> createTicket(@Header("Authorization") String authorizationHeader, @Body TicketDto ticketDto);
    @POST("/api/refuel/{vehicleid}")
    Call<Void> createRefuel(@Header("Authorization") String authorizationHeader, @Path("vehicleid") long vehicleId, @Body RefuelDto refuelDto);
    @POST("/api/costs/")
    Call<Void> createCost(@Header("Authorization") String authorizationHeader, @Body CostDto costDto);
    @GET("/api/auth/costs")
    Call<List<CostDto>> getCosts(@Header("Authorization") String authorizationHeader);
    @GET("/api/auth/refuel")
    Call<List<RefuelDto>> getRefuels(@Header("Authorization") String authorizationHeader);
    @GET("/api/auth/tickets")
    Call<List<TicketDto>> getTickets(@Header("Authorization") String authorizationHeader);
    @GET("/api/auth/vehicles")
    Call<List<VehicleDto>> getVehicleList(@Header("Authorization") String authorizationHeader);
    @GET("/api/auth/profile")
    Call<UserDto> getUserData(@Header("Authorization") String authorizationHeader);
    @GET("/api/vehicle/{id}")
    Call<VehicleDto> getVehicleDetails(@Header("Authorization") String authorization, @Path("id") long vehicleId);
}